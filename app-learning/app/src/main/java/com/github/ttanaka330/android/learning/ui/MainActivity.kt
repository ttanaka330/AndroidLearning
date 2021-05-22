package com.github.ttanaka330.android.learning.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.ttanaka330.android.learning.R
import com.github.ttanaka330.android.learning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
