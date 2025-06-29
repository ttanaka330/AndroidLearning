package com.github.ttanaka330.android.learning.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.ttanaka330.android.learning.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainRecyclerViewAdapter {
            val intent = Intent().setClassName(view.context, it.path)
            startActivity(intent)
        }
        binding.mainList.adapter = adapter

        activity?.let {
            val item = getLearningInfoList(it)
            adapter.submitList(item)
        }
    }

    private fun getLearningInfoList(parent: Activity): List<LearningInfo>? {
        try {
            val info = parent.packageManager.getPackageInfo(parent.packageName, PackageManager.GET_ACTIVITIES)
            return info.activities?.map {
                val packageName = getPackage(it.name)
                val className = getClassName(it.name)
                LearningInfo(packageName, className)
            }?.filter {
                it.packageName.indexOf(parent.packageName) != -1 &&
                    it.className != parent.javaClass.simpleName
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e)
        }
        return null
    }

    private fun getPackage(fullPath: String): String {
        val lastPos = fullPath.lastIndexOf('.')
        return fullPath.substring(0, lastPos)
    }

    private fun getClassName(fullPath: String): String {
        val lastPos = fullPath.lastIndexOf('.') + 1
        return fullPath.substring(lastPos)
    }
}
