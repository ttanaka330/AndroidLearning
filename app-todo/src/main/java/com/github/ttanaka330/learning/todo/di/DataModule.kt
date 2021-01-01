package com.github.ttanaka330.learning.todo.di

import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import com.github.ttanaka330.learning.todo.data.TodoDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single { Dispatchers.IO }
    single { TodoDatabase.createDatabase(get()) }
    single { get<TodoDatabase>().taskDao() }
    single<TaskRepository> { TaskRepositoryDataSource(get(), get()) }
}
