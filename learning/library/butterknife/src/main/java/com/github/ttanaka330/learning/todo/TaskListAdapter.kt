package com.github.ttanaka330.learning.todo

import android.content.res.ColorStateList
import android.os.Build
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    fun removeData(task: Task) {
        val position = getPosition(task)
        tasks.remove(task)
        notifyItemRemoved(position)
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
        holder.completed.apply {
            var isCompleted = item.completed
            setCheckboxColor(isCompleted)
            setOnClickListener {
                isCompleted = !isCompleted
                setCheckboxColor(isCompleted)
                actionListener.onCompletedChanged(item.copy(completed = isCompleted))
            }
        }
        holder.layout.setOnClickListener {
            actionListener.onTaskClick(item)
        }
    }

    private fun getPosition(task: Task): Int = tasks.indexOf(task)

    private fun ImageView.setCheckboxColor(isCompleted: Boolean) {
        val colorId = if (isCompleted) R.color.checkCompleted else R.color.checkActive
        val color = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            resources.getColor(colorId)
        } else {
            resources.getColor(colorId, null)
        }
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: ViewGroup = view.layout
        val title: TextView = view.title
        val description: TextView = view.description
        val completed: ImageView = view.completed
    }
}