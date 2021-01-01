package com.github.ttanaka330.learning.todo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ttanaka330.learning.todo.data.Task
import com.github.ttanaka330.learning.todo.data.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    fun loadList(): LiveData<List<Task>> = repository.loadList(false)

    fun updateCompleted(task: Task) = viewModelScope.launch {
        repository.save(task)
    }
}
