package com.github.ttanaka330.learning.webapi.openweathermap.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.ttanaka330.learning.webapi.openweathermap.R
import com.squareup.picasso.Picasso

object ViewBinding {
    private const val DIFF_CELSIUS = -273.15

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        Picasso.get().load(url).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("textKelvinToCelsius")
    fun textKelvinToCelsius(textView: TextView, kelvin: Double) {
        val celsius = kelvin + DIFF_CELSIUS
        textView.text = textView.context.getString(R.string.celsius, celsius)
    }
}
