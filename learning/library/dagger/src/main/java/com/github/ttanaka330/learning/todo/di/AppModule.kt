package com.github.ttanaka330.learning.todo.di

import android.app.Application
import android.content.Context
import com.github.ttanaka330.learning.todo.data.TaskRepository
import com.github.ttanaka330.learning.todo.data.TaskRepositoryDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesTaskRepository(context: Context): TaskRepository {
        return TaskRepositoryDataSource(context)
    }
}