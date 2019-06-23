package com.github.ttanaka330.learning.todo

import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.todo.data.Task
import kotlinx.android.synthetic.main.view_task.view.*

class TaskListAdapter(
    private val actionListener: ActionListener
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface ActionListener {
        fun onTaskClick(task: Task)
        fun onCompletedChanged(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.view_task, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
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

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}