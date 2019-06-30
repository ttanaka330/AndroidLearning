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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.databinding.FragmentTaskCompletedBinding
import com.github.ttanaka330.learning.todo.viewmodel.TaskCompletedViewModel
import com.github.ttanaka330.learning.todo.widget.ConfirmMessageDialog

class TaskCompletedFragment : BaseFragment(), TaskListAdapter.ActionListener {

    companion object {
        private const val REQUEST_DELETE_MESSAGE = 1
    }

    private lateinit var binding: FragmentTaskCompletedBinding
    private val viewModel: TaskCompletedViewModel by lazy {
        ViewModelProviders.of(this, TaskCompletedViewModel.Factory(requireContext()))
            .get(TaskCompletedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskCompletedBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupData()
        return binding.root
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

    private fun setupData() {
        binding.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskCompletedFragment)
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        viewModel.loadList().observe(viewLifecycleOwner, Observer { tasks ->
            (binding.list.adapter as TaskListAdapter).submitList(tasks)
        })
    }

    private fun deleteCompleted() {
        viewModel.deleteCompleted().observe(viewLifecycleOwner, Observer {
            setupData()
        })
    }

    private fun navigationDetail(taskId: Int) {
        val direction = TaskCompletedFragmentDirections.actionTaskCompletedToTaskDetail(taskId)
        findNavController(this).navigate(direction)
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
        viewModel.updateCompleted(task)
    }

    override fun onChangedItemCount(itemCount: Int) {
        binding.empty.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
    }
}
