package com.github.ttanaka330.learning.todo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryImpl
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskListFragment : Fragment(), TaskListAdapter.ActionListener {

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
        setupData(rootView)
        setupListener(rootView)
        return rootView
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryImpl.getInstance(context)

        val adapter = TaskListAdapter(context, this)
        val data = repository.loadAll()
        adapter.replaceData(data)
        view.list.adapter = adapter
        view.empty.visibility = if (data.isNotEmpty()) View.GONE else View.VISIBLE
    }

    private fun setupListener(view: View) {
        view.fab.setOnClickListener {
            navigationDetail()
        }
    }

    private fun navigationDetail(taskId: Int? = null) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, TaskDetailFragment.newInstance(taskId))
            ?.addToBackStack(null)
            ?.commit()
    }

    // ---------------------------------------------------------------------------------------------
    // ActionListener
    // ---------------------------------------------------------------------------------------------

    override fun onTaskClick(task: Task) {
        navigationDetail(task.id)
    }

    override fun onCompletedChanged(task: Task) {
        repository.save(task)
    }
}
