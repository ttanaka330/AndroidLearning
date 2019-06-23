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
import com.github.ttanaka330.learning.todo.databinding.FragmentTaskListBinding
import com.github.ttanaka330.learning.todo.viewmodel.TaskListViewModel

class TaskListFragment : BaseFragment(), TaskListAdapter.ActionListener {

    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this, TaskListViewModel.Factory(requireContext()))
            .get(TaskListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupData()
        setupListener()
        return binding.root
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

    private fun setupData() {
        binding.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskListFragment)
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        viewModel.loadList().observe(viewLifecycleOwner, Observer { tasks ->
            (binding.list.adapter as TaskListAdapter).let {
                it.submitList(tasks)
                binding.empty.visibility = if (it.itemCount > 0) View.GONE else View.VISIBLE
            }
        })
    }

    private fun setupListener() {
        binding.fab.setOnClickListener {
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
        viewModel.updateCompleted(task)
    }
}
