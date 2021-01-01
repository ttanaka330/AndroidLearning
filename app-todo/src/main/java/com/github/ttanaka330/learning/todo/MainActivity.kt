package com.github.ttanaka330.learning.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ttanaka330.learning.todo.ui.list.TaskListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TaskListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
