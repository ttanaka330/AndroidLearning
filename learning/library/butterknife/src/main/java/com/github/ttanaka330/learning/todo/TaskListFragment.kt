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
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource

class TaskListFragment : BaseFragment(), TaskListAdapter.ActionListener {

    companion object {
        fun newInstance() = TaskListFragment()
    }

    private lateinit var unbinder: Unbinder
    private lateinit var repository: TaskRepository

    @BindView(R.id.list)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.empty)
    lateinit var emptyView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
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
        inflater.inflate(R.menu.menu_tasks, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_show_completed) {
            navigationCompleted()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @OnClick(R.id.fab)
    fun onFabClicked() {
        navigationDetail()
    }

    private fun setupToolbar() {
        setToolBar(R.string.title_list)
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryDataSource.getInstance(context)
        val data = repository.loadList(false)

        recyclerView.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskListFragment).apply { replaceData(data) }
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        updateShowEmpty(view)
    }

    private fun updateShowEmpty(view: View) {
        view.apply {
            val count = recyclerView.adapter?.itemCount ?: 0
            emptyView.visibility = if (count > 0) View.GONE else View.VISIBLE
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
        recyclerView.adapter?.let {
            if (it is TaskListAdapter) {
                it.removeData(task)
                updateShowEmpty(view!!)
            }
        }
    }
}
