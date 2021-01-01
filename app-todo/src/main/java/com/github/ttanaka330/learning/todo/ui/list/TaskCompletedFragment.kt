package com.github.ttanaka330.learning.todo.ui.list

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
import com.github.ttanaka330.learning.todo.R
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.databinding.FragmentTaskCompletedBinding
import com.github.ttanaka330.learning.todo.ui.common.widget.BaseFragment
import com.github.ttanaka330.learning.todo.ui.common.widget.ConfirmMessageDialog
import com.github.ttanaka330.learning.todo.ui.detail.TaskDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskCompletedFragment : BaseFragment(), TaskListAdapter.ActionListener {

    private val viewModel: TaskCompletedViewModel by viewModel()
    private var viewBinding: FragmentTaskCompletedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTaskCompletedBinding.inflate(inflater, container, false)
        viewBinding = binding
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            val dialog = ConfirmMessageDialog.newInstance(R.string.message_confirm_delete_completed)
            dialog.setTargetFragment(this, REQUEST_DELETE_MESSAGE)
            dialog.show(parentFragmentManager, null)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setToolBar(R.string.title_completed, true)
        setHasOptionsMenu(true)
    }

    private fun setupData() {
        val context = context ?: return
        val binding = viewBinding ?: return
        val adapter = TaskListAdapter(this)
        binding.list.also {
            val orientation = RecyclerView.VERTICAL
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context, orientation, false)
            it.addItemDecoration(DividerItemDecoration(context, orientation))
        }

        viewModel.loadCompletedList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun navigationDetail(taskId: Int? = null) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, TaskDetailFragment.newInstance(taskId))
            .addToBackStack(null)
            .commit()
    }

    // ---------------------------------------------------------------------------------------------
    // Callback
    // ---------------------------------------------------------------------------------------------

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_DELETE_MESSAGE) {
            if (resultCode == DialogInterface.BUTTON_POSITIVE) {
                viewModel.deleteCompleted()
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
        val binding = viewBinding ?: return
        binding.empty.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
    }

    companion object {
        private const val REQUEST_DELETE_MESSAGE = 1

        fun newInstance() = TaskCompletedFragment()
    }
}
