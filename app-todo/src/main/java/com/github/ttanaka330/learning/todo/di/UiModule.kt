package com.github.ttanaka330.learning.todo.di

import com.github.ttanaka330.learning.todo.ui.detail.TaskDetailViewModel
import com.github.ttanaka330.learning.todo.ui.list.TaskCompletedViewModel
import com.github.ttanaka330.learning.todo.ui.list.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { TaskListViewModel(get()) }
    viewModel { TaskCompletedViewModel(get()) }
    viewModel { (id: Int) -> TaskDetailViewModel(id, get()) }
}
