package com.github.ttanaka330.learning.todo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import com.github.ttanaka330.learning.todo.widget.ConfirmMessageDialog
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskCompletedFragment : BaseFragment(), TaskListAdapter.ActionListener {

    companion object {
        private const val REQUEST_DELETE_MESSAGE = 1

        fun newInstance() = TaskCompletedFragment()
    }

    private lateinit var repository: TaskRepository
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_completed, container, false)
        setupToolbar()
        setupData(rootView)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            val dialog = ConfirmMessageDialog.newInstance(R.string.message_confirm_delete_completed)
            dialog.setTargetFragment(this, REQUEST_DELETE_MESSAGE)
            dialog.show(fragmentManager, null)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setToolBar(R.string.title_completed, true)
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryDataSource.getInstance(context)

        view.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskCompletedFragment)
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        updateTasks(view)
    }

    private fun updateTasks(view: View) {
        Single.just(repository.loadList(true))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { tasks ->
                (view.list.adapter as TaskListAdapter).let {
                    it.submitList(tasks)
                    view.empty.visibility = if (it.itemCount > 0) View.GONE else View.VISIBLE
                }
            }
            .addTo(compositeDisposable)
    }

    private fun deleteCompleted() {
        Completable.fromAction { repository.deleteCompleted() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { view?.let { setupData(it) } }
            .addTo(compositeDisposable)
    }

    private fun navigationDetail(taskId: Int? = null) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, TaskDetailFragment.newInstance(taskId))
            ?.addToBackStack(null)
            ?.commit()
    }

    // ---------------------------------------------------------------------------------------------
    // Callback
    // ---------------------------------------------------------------------------------------------

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_DELETE_MESSAGE) {
            if (resultCode == DialogInterface.BUTTON_POSITIVE) {
                deleteCompleted()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onTaskClick(task: Task) {
        navigationDetail(task.id)
    }

    override fun onCompletedChanged(task: Task) {
        Completable.fromAction { repository.save(task) }
            .andThen { view?.let { updateTasks(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}
