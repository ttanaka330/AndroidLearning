package com.github.ttanaka330.learning.todo.di

import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import org.koin.dsl.module.module

val appModule = module {
    single { TaskRepositoryDataSource(get()) as TaskRepository }
}