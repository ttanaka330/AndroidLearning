package com.github.ttanaka330.learning.todo.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.github.ttanaka330.learning.todo.R
import com.github.ttanaka330.learning.todo.databinding.FragmentTaskDetailBinding
import com.github.ttanaka330.learning.todo.ui.common.widget.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TaskDetailFragment : BaseFragment() {

    private val viewModel: TaskDetailViewModel by viewModel {
        val defaultId = TaskDetailViewModel.TASK_NONE_ID
        parametersOf(arguments?.getInt(ARG_TASK_ID, defaultId) ?: defaultId)
    }
    private var viewBinding: FragmentTaskDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        viewBinding = binding
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (viewModel.taskId == TaskDetailViewModel.TASK_NONE_ID) {
            menu.findItem(R.id.action_delete).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            viewModel.delete().observe(viewLifecycleOwner) {
                showSnackbar(getString(R.string.message_delete, it.title))
                back()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setToolBar(R.string.title_detail, true)
        setHasOptionsMenu(true)
    }

    private fun setupData() {
        val binding = viewBinding ?: return
        viewModel.taskDetail.observe(viewLifecycleOwner) {
            // Delete対策
            it ?: return@observe

            binding.title.setText(it.title)
            binding.description.setText(it.description)
            if (it.completed) {
                binding.title.isEnabled = false
                binding.description.isEnabled = false
                binding.save.visibility = View.GONE
            } else {
                binding.save.isEnabled = it.title.isNotBlank()
            }
        }

        binding.title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                binding.save.isEnabled = s.isNotBlank()
            }
        })
        binding.save.setOnClickListener {
            viewModel.save(
                title = binding.title.text.toString(),
                description = binding.description.text.toString()
            ).observe(viewLifecycleOwner) {
                back()
            }
        }
    }

    private fun back() {
        parentFragmentManager.popBackStack()
    }

    private fun showSnackbar(message: String) {
        activity?.findViewById<View>(android.R.id.content)?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARG_TASK_ID = "TASK_ID"

        fun newInstance(taskId: Int? = null) = TaskDetailFragment().apply {
            arguments = Bundle().apply {
                taskId?.let { putInt(ARG_TASK_ID, it) }
            }
        }
    }
}
