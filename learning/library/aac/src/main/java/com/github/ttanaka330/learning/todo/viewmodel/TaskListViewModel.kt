package com.github.ttanaka330.learning.todo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import java.util.concurrent.Executors

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            TaskListViewModel(TaskRepositoryDataSource.getInstance(context)) as T
    }

    private val io = Executors.newSingleThreadExecutor()

    fun loadList(): LiveData<List<Task>> = repository.loadList(false)

    fun updateCompleted(task: Task): LiveData<Unit> {
        val liveData: MutableLiveData<Unit> = MutableLiveData()
        io.execute {
            repository.save(task)
            liveData.postValue(Unit)
        }
        return liveData
    }
}