package com.github.ttanaka330.learning.todo

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {

    fun setToolBar(@StringRes titleId: Int, isHomeUpEnabled: Boolean = false) {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.setTitle(titleId)
            it.setDisplayHomeAsUpEnabled(isHomeUpEnabled)
        }
    }
}