package com.github.ttanaka330.learning.todo.realm.data

import io.realm.Realm
import io.realm.Sort

class TaskRepositoryDataSource private constructor() : TaskRepository {

    companion object {
        @Volatile
        private var INSTANCE: TaskRepositoryDataSource? = null

        fun getInstance(): TaskRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepositoryDataSource().also { INSTANCE = it }
            }
    }

    private val database: Realm = Realm.getDefaultInstance()

    override fun load(id: Int): Task? {
        val result = database.where(TaskObject::class.java)
            .equalTo(TaskObject::id.name, id)
            .findFirst()
        return result?.toModel()
    }

    override fun loadList(isCompleted: Boolean): List<Task> {
        val tasks: MutableList<Task> = mutableListOf()
        val results = database.where(TaskObject::class.java)
            .equalTo(TaskObject::completed.name, isCompleted)
            .sort(TaskObject::id.name, Sort.DESCENDING)
            .findAll()
        results.forEach { tasks.add(it.toModel()) }
        return tasks
    }

    override fun save(task: Task) {
        if (task.id == null) {
            database.executeTransaction {
                val taskObject = database.createObject(TaskObject::class.java, incrementId())
                taskObject.setTask(task)
                it.insertOrUpdate(taskObject)
            }
        } else {
            database.executeTransaction {
                it.copyToRealmOrUpdate(task.toEntity())
            }
        }
    }

    override fun delete(id: Int) {
        database.executeTransaction { realm ->
            realm.where(TaskObject::class.java)
                .equalTo(TaskObject::id.name, id)
                .findFirst()
                ?.deleteFromRealm()
        }
    }

    override fun deleteCompleted() {
        database.executeTransaction { realm ->
            realm.where(TaskObject::class.java)
                .equalTo(TaskObject::completed.name, true)
                .findAll()
                .forEach { it.deleteFromRealm() }
        }
    }

    private fun incrementId(): Int {
        val max = database.where(TaskObject::class.java).max(TaskObject::id.name)?.toInt() ?: 0
        return max + 1
    }

    private fun Task.toEntity(): TaskObject =
        TaskObject().also {
            it.id = this.id ?: incrementId()
            it.title = this.title
            it.description = this.description
            it.completed = this.completed
        }

    private fun TaskObject.toModel(): Task =
        Task(
            id = this.id,
            title = this.title,
            description = this.description,
            completed = this.completed
        )

    private fun TaskObject.setTask(task: Task) {
        title = task.title
        description = task.description
        completed = task.completed
    }
}