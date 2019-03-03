package com.github.ttanaka330.learning.todo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import butterknife.Unbinder
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource

class TaskDetailFragment : BaseFragment() {

    companion object {
        private const val ARG_TASK_ID = "TASK_ID"

        fun newInstance(taskId: Int? = null) = TaskDetailFragment().apply {
            arguments = Bundle().apply {
                taskId?.let { putInt(ARG_TASK_ID, it) }
            }
        }
    }

    private lateinit var unbinder: Unbinder
    private lateinit var repository: TaskRepository
    private lateinit var task: Task
    private var taskId: Int? = null

    @BindView(R.id.title)
    lateinit var editTitle: EditText
    @BindView(R.id.description)
    lateinit var editDescription: EditText
    @BindView(R.id.save)
    lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_TASK_ID)) {
                taskId = it.getInt(ARG_TASK_ID)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_detail, container, false)
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (taskId == null) {
            menu.findItem(R.id.action_delete).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            taskId?.let {
                repository.delete(it)
                showSnackbar(getString(R.string.message_delete, task.title))
            }
            back()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @OnTextChanged(R.id.title, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun onTitleChanged(editable: Editable) {
        buttonSave.isEnabled = editable.isNotBlank()
    }

    @OnClick(R.id.save)
    fun onSaveClicked() {
        repository.save(
            task.copy(
                title = editTitle.text.toString(),
                description = editDescription.text.toString()
            )
        )
        back()
    }

    private fun setupToolbar() {
        setToolBar(R.string.title_detail, true)
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryDataSource.getInstance(context)
        task = taskId?.let { repository.load(it) } ?: Task()

        editTitle.setText(task.title)
        editDescription.setText(task.description)
        if (task.completed) {
            editTitle.isEnabled = false
            editDescription.isEnabled = false
            buttonSave.visibility = View.GONE
        } else {
            buttonSave.isEnabled = task.title.isNotBlank()
        }
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
