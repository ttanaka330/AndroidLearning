package com.github.ttanaka330.learning.todo.di

import com.github.ttanaka330.learning.todo.MainActivity
import com.github.ttanaka330.learning.todo.TaskCompletedFragment
import com.github.ttanaka330.learning.todo.TaskDetailFragment
import com.github.ttanaka330.learning.todo.TaskListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeTaskListFragment(): TaskListFragment

    @ContributesAndroidInjector
    abstract fun contributeTaskCompletedFragment(): TaskCompletedFragment

    @ContributesAndroidInjector
    abstract fun contributeTaskDetailFragment(): TaskDetailFragment
}