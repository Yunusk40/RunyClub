package com.example.runyclub.tracking.service

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.CameraPosition
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultTrackingServiceManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TrackingServiceManager {

    private val _cameraPositionUpdate = MutableLiveData<CameraPosition>()
    override val cameraPositionUpdate: LiveData<CameraPosition> = _cameraPositionUpdate

    override fun startService() {
        Intent(context, TrackingService::class.java).apply {
            action = TrackingService.ACTION_START_SERVICE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(this)
            } else context.startService(this)
        }
    }

    override fun stopService() {
        Intent(context, TrackingService::class.java).apply {
            context.stopService(this)
        }
    }

    override fun updateCameraPosition(cameraPosition: CameraPosition) {
        _cameraPositionUpdate.postValue(cameraPosition)
    }

}