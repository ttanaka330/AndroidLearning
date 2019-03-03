package com.github.ttanaka330.learning.todo

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

open class BaseFragment : Fragment() {

    fun setToolBar(@StringRes titleId: Int, isHomeUpEnabled: Boolean = false) {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.setTitle(titleId)
            it.setDisplayHomeAsUpEnabled(isHomeUpEnabled)
        }
    }
}