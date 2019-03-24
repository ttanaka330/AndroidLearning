package com.github.ttanaka330.learning.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.viewmodel.TaskListViewModel
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskListFragment : BaseFragment(), TaskListAdapter.ActionListener {

    private val listAdapter = TaskListAdapter(this)
    private val viewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this, TaskListViewModel.Factory(requireContext()))
            .get(TaskListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        setHasOptionsMenu(true)
        setupData(rootView)
        setupListener(rootView)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_tasks, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_show_completed) {
            navigationCompleted()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupData(view: View) {
        val context = view.context
        view.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        viewModel.loadList().observe(viewLifecycleOwner, Observer {
            listAdapter.replaceData(it)
            view.empty.visibility = if (listAdapter.itemCount > 0) View.GONE else View.VISIBLE
        })
    }

    private fun setupListener(view: View) {
        view.fab.setOnClickListener {
            navigationDetail()
        }
    }

    private fun navigationDetail(taskId: Int = Task.NEW_TASK_ID) {
        val direction = TaskListFragmentDirections.actionTaskListToTaskDetail(taskId)
        findNavController(this).navigate(direction)
    }

    private fun navigationCompleted() {
        val direction = TaskListFragmentDirections.actionTaskListToTaskCompleted()
        findNavController(this).navigate(direction)
    }

    // ---------------------------------------------------------------------------------------------
    // Callback
    // ---------------------------------------------------------------------------------------------

    override fun onTaskClick(task: Task) {
        navigationDetail(task.id)
    }

    override fun onCompletedChanged(task: Task) {
        viewModel.updateCompleted(task).observe(viewLifecycleOwner, Observer {
            listAdapter.removeData(task)
        })
    }
}
