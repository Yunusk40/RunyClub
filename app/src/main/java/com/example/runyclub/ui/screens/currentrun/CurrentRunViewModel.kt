package com.example.runyclub.ui.screens.currentrun

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runyclub.database.model.Run
import com.example.runyclub.database.repository.AppRepository
import com.example.runyclub.core.tracking.TrackingManager
import com.example.runyclub.di.ApplicationScope
import com.example.runyclub.di.IoDispatcher
import com.example.runyclub.domain.CurrentRunStateWithCalories
import com.example.runyclub.domain.GetCurrentRunStateWithCaloriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject
import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.example.runyclub.core.tracking.service.TrackingServiceManager

@HiltViewModel
class CurrentRunViewModel @Inject constructor(
    private val trackingManager: TrackingManager,
    private val trackingServiceManager: TrackingServiceManager, // Correctly inject TrackingServiceManager
    private val repository: AppRepository,
    @ApplicationScope
    private val appCoroutineScope: CoroutineScope,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    getCurrentRunStateWithCaloriesUseCase: GetCurrentRunStateWithCaloriesUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {
    val currentRunStateWithCalories = getCurrentRunStateWithCaloriesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CurrentRunStateWithCalories()
        )
    val runningDurationInMillis = trackingManager.trackingDurationInMs

    val currentLocation = trackingManager.currentLocation

    init {
        fetchCurrentLocation()
    }

    fun playPauseTracking() {
        if (currentRunStateWithCalories.value.currentRunState.isTracking)
            trackingManager.pauseTracking()
        else trackingManager.startResumeTracking()
    }

    fun finishRun(bitmap: Bitmap) {
        trackingManager.pauseTracking()
        saveRun(
            Run(
                img = bitmap,
                avgSpeedInKMH = currentRunStateWithCalories.value.currentRunState.distanceInMeters
                    .toBigDecimal()
                    .multiply(3600.toBigDecimal())
                    .divide(runningDurationInMillis.value.toBigDecimal(), 2, RoundingMode.HALF_UP)
                    .toFloat(),
                distanceInMeters = currentRunStateWithCalories.value.currentRunState.distanceInMeters,
                durationInMillis = runningDurationInMillis.value,
                timestamp = Date(),
                caloriesBurned = currentRunStateWithCalories.value.caloriesBurnt
            )
        )
        trackingManager.stop()
    }

    private fun saveRun(run: Run) = appCoroutineScope.launch(ioDispatcher) {
        repository.insertRun(run)
    }


    @SuppressLint("MissingPermission")
    private fun fetchCurrentLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLocation = LatLng(location.latitude, location.longitude)
                updateCameraPosition(currentLocation)
            }
        }
    }

    private fun updateCameraPosition(location: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(location)
            .zoom(15f) // Adjust zoom level as needed
            .build()
        trackingServiceManager.updateCameraPosition(cameraPosition)
    }

}