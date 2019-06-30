package com.github.ttanaka330.learning.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        return ViewHolder(ViewTaskBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
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

    inner class ViewHolder(var binding: ViewTaskBinding) : RecyclerView.ViewHolder(binding.root)

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