package com.github.ttanaka330.learning.todo

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
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
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
        repository = TaskRepositoryDataSource.getInstance()
        val data = repository.loadList(false)

        view.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskListFragment).apply { replaceData(data) }
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        updateShowEmpty(view)
    }

    private fun setupListener(view: View) {
        view.fab.setOnClickListener {
            navigationDetail()
        }
    }

    private fun updateShowEmpty(view: View) {
        view.apply {
            val count = list?.adapter?.itemCount ?: 0
            empty.visibility = if (count > 0) View.GONE else View.VISIBLE
        }
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
        view?.list?.adapter?.let {
            if (it is TaskListAdapter) {
                it.removeData(task)
                updateShowEmpty(view!!)
            }
        }
    }
}
