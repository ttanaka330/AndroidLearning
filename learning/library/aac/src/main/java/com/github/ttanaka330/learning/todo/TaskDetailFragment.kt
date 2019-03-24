package com.github.ttanaka330.learning.todo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.github.ttanaka330.learning.todo.viewmodel.TaskDetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_task_detail.view.*

class TaskDetailFragment : BaseFragment() {

    private val viewModel: TaskDetailViewModel by lazy {
        val taskId = TaskDetailFragmentArgs.fromBundle(arguments!!).taskId
        val factory = TaskDetailViewModel.Factory(requireContext(), taskId)
        ViewModelProviders.of(this, factory).get(TaskDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_detail, container, false)
        setHasOptionsMenu(true)
        setupData(rootView)
        setupListener(rootView)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (viewModel.isNewTask()) {
            menu.findItem(R.id.action_delete).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            viewModel.deleteTask().observe(viewLifecycleOwner, Observer {
                showSnackbar(getString(R.string.message_delete, view!!.title.text))
                back()
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupData(view: View) {
        viewModel.loadTask().observe(viewLifecycleOwner, Observer { task ->
            view.title.setText(task.title)
            view.description.setText(task.description)
            if (task.completed) {
                view.title.isEnabled = false
                view.description.isEnabled = false
                view.save.visibility = View.GONE
            } else {
                view.save.isEnabled = task.title.isNotBlank()
            }
        })
    }

    private fun setupListener(view: View) {
        view.title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                view.save.isEnabled = s.isNotBlank()
            }
        })
        view.save.setOnClickListener {
            viewModel.saveTask(
                view.title.text.toString(),
                view.description.text.toString()
            ).observe(viewLifecycleOwner, Observer { back() })
        }
    }

    private fun back() {
        findNavController(this).popBackStack()
    }

    private fun showSnackbar(message: String) {
        activity?.findViewById<View>(android.R.id.content)?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}
