package com.github.ttanaka330.android.learning.maps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.ttanaka330.android.learning.maps.R
import com.github.ttanaka330.android.learning.maps.databinding.FragmentLearningMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class LearningMapsFragment : Fragment() {

    private var _binding: FragmentLearningMapsBinding? = null
    private val binding: FragmentLearningMapsBinding
        get() = _binding!!

    private var map: GoogleMap? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap.also {
            it.uiSettings.isMyLocationButtonEnabled = false

            // 初期位置として東京駅を表示
            val defaultPosition = LatLng(35.681236, 139.767125)
            val cameraPosition = CameraPosition.Builder()
                .target(defaultPosition)
                .zoom(15f)
                .build()
            it.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearningMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(callback)
    }
}
