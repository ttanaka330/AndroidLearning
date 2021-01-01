package com.github.ttanaka330.learning.todo.ui.list

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.todo.R
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.databinding.ViewTaskBinding

class TaskListAdapter(
    private val actionListener: ActionListener
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface ActionListener {
        fun onTaskClick(task: Task)
        fun onCompletedChanged(task: Task)
        fun onChangedItemCount(itemCount: Int)
    }

    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                actionListener.onChangedItemCount(getItemCount())
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                actionListener.onChangedItemCount(getItemCount())
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ViewTaskBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.viewBinding.title.text = item.title
        holder.viewBinding.description.text = item.description
        holder.viewBinding.completed.apply {
            var isCompleted = item.completed
            setCheckboxColor(isCompleted)
            setOnClickListener {
                isCompleted = !isCompleted
                setCheckboxColor(isCompleted)
                actionListener.onCompletedChanged(item.copy(completed = isCompleted))
            }
        }
        holder.viewBinding.layout.setOnClickListener {
            actionListener.onTaskClick(item)
        }
    }

    private fun ImageView.setCheckboxColor(isCompleted: Boolean) {
        val colorId = if (isCompleted) R.color.checkCompleted else R.color.checkActive
        val color = ContextCompat.getColor(context, colorId)
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
    }

    inner class ViewHolder(val viewBinding: ViewTaskBinding) : RecyclerView.ViewHolder(viewBinding.root)

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
