package com.github.ttanaka330.learning.todo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.github.ttanaka330.learning.todo.data.Task
import kotlinx.android.synthetic.main.view_task.view.*

class TaskListAdapter(
    private val actionListener: ActionListener
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private val tasks: MutableList<Task> = mutableListOf()

    interface ActionListener {
        fun onTaskClick(task: Task)
        fun onCompletedChanged(task: Task)
    }

    fun replaceData(tasks: List<Task>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.view_task, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.completed.setOnCheckedChangeListener(null)
        holder.completed.apply {
            isChecked = item.completed
            setOnCheckedChangeListener { buttonView, isChecked ->
                buttonView.isChecked = isChecked
                actionListener.onCompletedChanged(item.copy(completed = isChecked))
            }
        }
        holder.layout.setOnClickListener {
            actionListener.onTaskClick(item)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: ViewGroup = view.layout
        val title: TextView = view.title
        val description: TextView = view.description
        val completed: CheckBox = view.completed
    }
}