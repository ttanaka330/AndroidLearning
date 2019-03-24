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

class TaskDetailViewModel(
    private val repository: TaskRepository,
    private val taskId: Int
) : ViewModel() {

    class Factory(
        private val context: Context,
        private val taskId: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            TaskDetailViewModel(TaskRepositoryDataSource.getInstance(context), taskId) as T
    }

    private val io = Executors.newSingleThreadExecutor()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val completed: MutableLiveData<Boolean> = MutableLiveData()

    fun isNewTask(): Boolean = (taskId == Task.NEW_TASK_ID)

    fun loadTask() {
        val liveData: LiveData<Task>
        when (taskId) {
            Task.NEW_TASK_ID -> {
                liveData = MutableLiveData()
                io.execute { liveData.postValue(Task()) }
            }
            else -> {
                liveData = repository.load(taskId)
            }
        }
        liveData.observeForever {
            it?.let { task ->
                title.postValue(task.title)
                description.postValue(task.description)
                completed.postValue(task.completed)
            }
        }
    }

    fun saveTask(): LiveData<Unit> {
        val liveData: MutableLiveData<Unit> = MutableLiveData()
        io.execute {
            repository.save(
                Task(
                    id = taskId,
                    title = title.value!!,
                    description = description.value!!,
                    completed = completed.value!!
                )
            )
            liveData.postValue(Unit)
        }
        return liveData
    }

    fun deleteTask(): LiveData<Unit> {
        val liveData: MutableLiveData<Unit> = MutableLiveData()
        io.execute {
            repository.delete(taskId)
            liveData.postValue(Unit)
        }
        return liveData
    }
}