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
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_task_detail.view.*

class TaskDetailFragment : BaseFragment() {

    private lateinit var repository: TaskRepository
    private lateinit var task: Task
    private var taskId: Int = Task.NEW_TASK_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskId = TaskDetailFragmentArgs.fromBundle(arguments!!).taskId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_detail, container, false)
        setHasOptionsMenu(true)
        setupData(rootView)
        setupListener(rootView)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (taskId == Task.NEW_TASK_ID) {
            menu.findItem(R.id.action_delete).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            repository.delete(taskId)
            showSnackbar(getString(R.string.message_delete, task.title))
            back()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryDataSource.getInstance(context)
        task = when (taskId) {
            Task.NEW_TASK_ID -> Task()
            else -> repository.load(taskId) ?: Task()
        }

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

    private fun setupListener(view: View) {
        view.title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                view.save.isEnabled = s.isNotBlank()
            }
        })
        view.save.setOnClickListener {
            repository.save(
                task.copy(
                    title = view.title.text.toString(),
                    description = view.description.text.toString()
                )
            )
            back()
        }
    }

    private fun back() {
        findNavController(this).popBackStack()
    }

    private fun showSnackbar(message: String) {
        activity?.findViewById<View>(android.R.id.content)?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
