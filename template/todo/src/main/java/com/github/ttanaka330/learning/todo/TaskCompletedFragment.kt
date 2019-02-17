package com.github.ttanaka330.learning.todo

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryImpl
import com.github.ttanaka330.learning.todo.widget.ConfirmMessageDialog
import kotlinx.android.synthetic.main.fragment_task_list.view.*

class TaskCompletedFragment : Fragment(), TaskListAdapter.ActionListener,
    ConfirmMessageDialog.ConfirmDialogListener {

    companion object {
        fun newInstance() = TaskCompletedFragment()
    }

    private lateinit var repository: TaskRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_completed, container, false)
        setupToolbar()
        setupData(rootView)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            ConfirmMessageDialog
                .newInstance(R.string.message_confirm_delete_completed)
                .show(childFragmentManager, null)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
    }

    private fun setupData(view: View) {
        val context = view.context
        repository = TaskRepositoryImpl.getInstance(context)
        val data = repository.loadList(true)

        view.list.apply {
            val orientation = RecyclerView.VERTICAL
            adapter = TaskListAdapter(this@TaskCompletedFragment).apply { replaceData(data) }
            layoutManager = LinearLayoutManager(context, orientation, false)
            addItemDecoration(DividerItemDecoration(context, orientation))
        }
        updateShowEmpty(view)
    }

    private fun updateShowEmpty(view: View) {
        view.apply {
            val count = list?.adapter?.itemCount ?: 0
            empty.visibility = if (count > 0) View.GONE else View.VISIBLE
        }
    }

    private fun deteleCompleted() {
        repository.deleteCompleted()
        view?.let { setupData(it) }
    }

    private fun navigationDetail(taskId: Int? = null) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, TaskDetailFragment.newInstance(taskId))
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

    override fun onDialogResult(which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            deteleCompleted()
        }
    }
}
