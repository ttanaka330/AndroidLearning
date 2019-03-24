package com.github.ttanaka330.learning.todo.data

data class Task(
    val id: Int = NEW_TASK_ID,
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false
) {

    companion object {
        const val NEW_TASK_ID = -1
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Task) return false
        return other.id == this.id
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + completed.hashCode()
        return result
    }
}