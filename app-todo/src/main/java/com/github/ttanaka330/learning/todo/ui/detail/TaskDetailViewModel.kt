package com.github.ttanaka330.learning.todo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository

class TaskDetailViewModel(
    val taskId: Int,
    private val repository: TaskRepository
) : ViewModel() {

    val taskDetail: LiveData<Task?> = liveData {
        if (taskId == TASK_NONE_ID) {
            emit(Task(id = null))
        } else {
            emitSource(repository.load(taskId))
        }
    }

    fun delete(): LiveData<Task> = liveData {
        taskDetail.value?.let { task ->
            task.id?.let { repository.delete(it) }
            emit(task)
        }
    }

    fun save(title: String, description: String): LiveData<Unit> = liveData {
        taskDetail.value?.let { task ->
            val updateTask = task.copy(
                title = title,
                description = description
            )
            repository.save(updateTask)
            emit(Unit)
        }
    }

    companion object {
        const val TASK_NONE_ID = 0
    }
}
