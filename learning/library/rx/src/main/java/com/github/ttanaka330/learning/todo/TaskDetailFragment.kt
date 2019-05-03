package com.github.ttanaka330.learning.todo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_task_detail.view.*

class TaskDetailFragment : BaseFragment() {

    companion object {
        private const val ARG_TASK_ID = "TASK_ID"

        fun newInstance(taskId: Int? = null) = TaskDetailFragment().apply {
            arguments = Bundle().apply {
                taskId?.let { putInt(ARG_TASK_ID, it) }
            }
        }
    }

    private lateinit var repository: TaskRepository
    private lateinit var task: Task
    private var taskId: Int? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_TASK_ID)) {
                taskId = it.getInt(ARG_TASK_ID)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_detail, container, false)
        setupToolbar()
        setupData(rootView)
        setupListener(rootView)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (taskId == null) {
            menu.findItem(R.id.action_delete).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            Completable.fromAction { taskId?.let { repository.delete(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    showSnackbar(getString(R.string.message_delete, task.title))
                    back()
                }
                .addTo(compositeDisposable)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setToolBar(R.string.title_detail, true)
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryDataSource.getInstance(context)

        Single.just(taskId?.let { repository.load(it) } ?: Task())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                task = it
                view.title.setText(task.title)
                view.description.setText(task.description)
                if (task.completed) {
                    view.title.isEnabled = false
                    view.description.isEnabled = false
                    view.save.visibility = View.GONE
                } else {
                    view.save.isEnabled = task.title.isNotBlank()
                }
            }
            .addTo(compositeDisposable)
    }

    private fun setupListener(view: View) {
        view.title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                view.save.isEnabled = s.isNotBlank()
            }
        })
        view.save.setOnClickListener {
            Completable.fromAction { saveTask(view) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { back() }
                .addTo(compositeDisposable)
        }
    }

    private fun saveTask(view: View) {
        repository.save(
            task.copy(
                title = view.title.text.toString(),
                description = view.description.text.toString()
            )
        )
    }

    private fun back() {
        fragmentManager?.popBackStack()
    }

    private fun showSnackbar(message: String) {
        activity?.findViewById<View>(android.R.id.content)?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
