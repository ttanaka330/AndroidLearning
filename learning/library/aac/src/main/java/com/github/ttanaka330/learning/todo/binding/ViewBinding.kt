package com.github.ttanaka330.learning.todo.binding

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("tintBind")
fun setTintBind(imageView: ImageView, color: Int) {
    ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color))
}