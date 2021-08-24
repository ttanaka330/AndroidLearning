package com.github.ttanaka330.android.learning.maps.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ttanaka330.android.learning.maps.databinding.ActivityLearningMapsBinding

class LearningMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearningMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearningMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
