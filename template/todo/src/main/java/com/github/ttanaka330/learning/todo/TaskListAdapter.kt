package com.github.ttanaka330.learning.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.github.ttanaka330.learning.todo.data.Task
import kotlinx.android.synthetic.main.view_task.view.*

class TaskListAdapter(
    context: Context,
    private val actionListener: ActionListener
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
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

    override fun getCount(): Int = tasks.size

    override fun getItem(position: Int): Task? = tasks.getOrNull(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.view_task, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = getItem(position)!!
        viewHolder.title.text = item.title
        viewHolder.description.text = item.description
        viewHolder.completed.setOnCheckedChangeListener(null)
        viewHolder.completed.isChecked = item.completed
        viewHolder.completed.setOnCheckedChangeListener { _, isChecked ->
            viewHolder.completed.isChecked = isChecked
            actionListener.onCompletedChanged(item.copy(completed = isChecked))
        }
        view.setOnClickListener {
            actionListener.onTaskClick(item)
        }
        return view
    }

    inner class ViewHolder(view: View) {
        val title: TextView = view.title
        val description: TextView = view.description
        val completed: CheckBox = view.completed
    }
}