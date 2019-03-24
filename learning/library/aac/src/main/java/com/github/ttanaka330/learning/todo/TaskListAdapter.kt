package com.github.ttanaka330.learning.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.databinding.ViewTaskBinding

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

    fun removeData(task: Task) {
        val position = getPosition(task)
        tasks.remove(task)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ViewTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        with(holder.binding) {
            task = item
            completed.setOnClickListener {
                val changeTask = item.copy(completed = !item.completed)
                task = changeTask
                actionListener.onCompletedChanged(changeTask)
            }
            layout.setOnClickListener {
                actionListener.onTaskClick(item)
            }
        }
    }

    private fun getPosition(task: Task): Int = tasks.indexOf(task)

    inner class ViewHolder(var binding: ViewTaskBinding) : RecyclerView.ViewHolder(binding.root)
}