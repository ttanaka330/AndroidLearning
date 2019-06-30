package com.github.ttanaka330.learning.todo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import com.github.ttanaka330.learning.todo.widget.ConfirmMessageDialog
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskCompletedFragment : BaseFragment(), TaskListAdapter.ActionListener {

    companion object {
        private const val REQUEST_DELETE_MESSAGE = 1

        fun newInstance() = TaskCompletedFragment()
    }

    private lateinit var unbinder: Unbinder
    private lateinit var repository: TaskRepository

    @BindView(R.id.list)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.empty)
    lateinit var emptyView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_completed, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        setupToolbar()
        setupData(rootView)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
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

        recyclerView.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskCompletedFragment)
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        // onCreateView時点では view==null のため、Handlerで処理を後ろにずらす
        Handler().post { updateTasks() }
    }

    private fun updateTasks() {
        val data = repository.loadList(true)
        (recyclerView.adapter as TaskListAdapter).submitList(data)
    }

    private fun deleteCompleted() {
        repository.deleteCompleted()
        view?.let { setupData(it) }
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
        repository.save(task)
        updateTasks()
    }

    override fun onChangedItemCount(itemCount: Int) {
        view?.empty?.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
    }
}
