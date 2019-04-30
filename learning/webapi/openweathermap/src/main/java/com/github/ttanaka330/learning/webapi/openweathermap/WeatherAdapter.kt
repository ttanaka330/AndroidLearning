package com.github.ttanaka330.learning.webapi.openweathermap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.ttanaka330.learning.webapi.openweathermap.model.Weather

class WeatherAdapter : ListAdapter<Weather, WeatherAdapter.DataBindingViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = R.layout.item_weather

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataBindingViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: Weather) {
            binding.setVariable(BR.model, weather)
            binding.executePendingBindings()
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.datetime == newItem.datetime
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}