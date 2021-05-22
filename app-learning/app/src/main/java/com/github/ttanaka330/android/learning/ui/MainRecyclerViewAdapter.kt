package com.github.ttanaka330.android.learning.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.android.learning.databinding.ItemMainBinding

class MainRecyclerViewAdapter(
    private val itemClickListener: (LearningInfo) -> Unit
) : ListAdapter<LearningInfo, MainRecyclerViewAdapter.ViewHolder>(MainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item)
    }

    inner class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(item: LearningInfo) {
            binding.className.text = item.className
            binding.root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }
}

class MainDiffCallback : DiffUtil.ItemCallback<LearningInfo>() {
    override fun areItemsTheSame(oldItem: LearningInfo, newItem: LearningInfo): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: LearningInfo, newItem: LearningInfo): Boolean {
        return oldItem == newItem
    }
}
