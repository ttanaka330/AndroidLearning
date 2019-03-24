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
    private var task: Task? = null

    fun isNewTask(): Boolean = (taskId == Task.NEW_TASK_ID)

    fun loadTask(): LiveData<Task> {
        if (task != null) {
            return MutableLiveData()
        } else {
            return when (taskId) {
                Task.NEW_TASK_ID -> {
                    val liveData: MutableLiveData<Task> = MutableLiveData()
                    io.execute {
                        task = Task()
                        liveData.postValue(task)
                    }
                    liveData
                }
                else -> {
                    val liveData = repository.load(taskId)
                    liveData.observeForever { task = it }
                    return liveData
                }
            }
        }
    }

    fun saveTask(title: String, description: String): LiveData<Unit> {
        val liveData: MutableLiveData<Unit> = MutableLiveData()
        io.execute {
            repository.save(
                task!!.copy(
                    title = title,
                    description = description
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