package com.android.learning.common.ext

import android.app.Activity
import androidx.fragment.app.Fragment

inline fun <reified T> Activity.lazyWithArgs(key: String): Lazy<T> =
    lazy { intent.extras.get(key) as T }

inline fun <reified T> Fragment.lazyWithArgs(key: String): Lazy<T> =
    lazy { arguments!!.get(key) as T }