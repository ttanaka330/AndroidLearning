package com.github.ttanaka330.learning.todo.ui.list

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
import com.github.ttanaka330.learning.todo.ui.common.widget.BaseFragment
import com.github.ttanaka330.learning.todo.ui.detail.TaskDetailFragment
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : BaseFragment(), TaskListAdapter.ActionListener {

    private val viewModel: TaskListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        setupToolbar()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData(view)
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

    private fun setupToolbar() {
        setToolBar(R.string.title_list)
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        val adapter = TaskListAdapter(this)
        view.list.also {
            val orientation = RecyclerView.VERTICAL
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context, orientation, false)
            it.addItemDecoration(DividerItemDecoration(context, orientation))
        }

        view.fab.setOnClickListener {
            navigationDetail()
        }

        viewModel.loadList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun navigationDetail(taskId: Int? = null) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, TaskDetailFragment.newInstance(taskId))
            .addToBackStack(null)
            .commit()
    }

    private fun navigationCompleted() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, TaskCompletedFragment.newInstance())
            .addToBackStack(null)
            .commit()
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

    override fun onChangedItemCount(itemCount: Int) {
        view?.empty?.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
    }

    companion object {
        fun newInstance() = TaskListFragment()
    }
}
