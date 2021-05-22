package com.github.ttanaka330.android.learning.ui

data class LearningInfo(
    val packageName: String,
    val className: String
) {
    val path = "$packageName.$className"
}
