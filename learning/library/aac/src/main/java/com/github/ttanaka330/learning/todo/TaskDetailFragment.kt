package com.github.ttanaka330.learning.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.github.ttanaka330.learning.todo.databinding.FragmentTaskDetailBinding
import com.github.ttanaka330.learning.todo.viewmodel.TaskDetailViewModel
import com.google.android.material.snackbar.Snackbar

class TaskDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentTaskDetailBinding
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
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        setupData()
        setupListener()
        return binding.root
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
                showSnackbar(getString(R.string.message_delete, viewModel.title.value))
                back()
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupData() {
        binding.viewModel = viewModel
        viewModel.loadTask()
    }

    private fun setupListener() {
        viewModel.title.observe(viewLifecycleOwner, Observer {
            binding.save.isEnabled = it.isNotBlank()
        })
        binding.save.setOnClickListener {
            viewModel.saveTask().observe(viewLifecycleOwner, Observer { back() })
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
