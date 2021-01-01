package com.github.ttanaka330.learning.todo.ui.list

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
import com.github.ttanaka330.learning.todo.R
import com.github.ttanaka330.learning.todo.ui.detail.TaskDetailFragment
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import com.github.ttanaka330.learning.todo.ui.common.widget.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskListFragment : BaseFragment(), TaskListAdapter.ActionListener {

    companion object {
        fun newInstance() = TaskListFragment()
    }

    private lateinit var repository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        setupToolbar()
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

    private fun setupToolbar() {
        setToolBar(R.string.title_list)
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryDataSource.getInstance(context)

        view.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskListFragment)
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        // onCreateView時点では view==null のため、Handlerで処理を後ろにずらす
        Handler().post { updateTasks(view) }
    }

    private fun setupListener(view: View) {
        view.fab.setOnClickListener {
            navigationDetail()
        }
    }

    private fun updateTasks(view: View) {
        val data = repository.loadList(false)
        (view.list.adapter as TaskListAdapter).submitList(data)
    }

    private fun navigationDetail(taskId: Int? = null) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, TaskDetailFragment.newInstance(taskId))
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun navigationCompleted() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, TaskCompletedFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }

    // ---------------------------------------------------------------------------------------------
    // Callback
    // ---------------------------------------------------------------------------------------------

    override fun onTaskClick(task: Task) {
        navigationDetail(task.id)
    }

    override fun onCompletedChanged(task: Task) {
        repository.save(task)
        view?.let { updateTasks(it) }
    }

    override fun onChangedItemCount(itemCount: Int) {
        view?.empty?.visibility = if (itemCount > 0) View.GONE else View.VISIBLE
    }
}
