package com.github.ttanaka330.learning.webapi.openweathermap.model

data class City(
    val id: String,
    val name: String
) {
    override fun toString(): String = name
}