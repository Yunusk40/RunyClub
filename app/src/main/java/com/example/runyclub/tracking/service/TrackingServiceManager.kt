package com.example.runyclub.tracking.service

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.CameraPosition

interface TrackingServiceManager {
    fun startService()
    fun stopService()
    val cameraPositionUpdate: LiveData<CameraPosition>
    fun updateCameraPosition(cameraPosition: CameraPosition)
}