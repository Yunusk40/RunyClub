package com.example.runyclub.tracking

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.example.runyclub.common.utils.LocationUtils
import com.example.runyclub.tracking.location.LocationTrackingManager
import com.example.runyclub.tracking.model.CurrentRunState
import com.example.runyclub.tracking.model.PathPoint
import com.example.runyclub.tracking.service.TrackingServiceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Singleton

// Manages tracking of user's running activities, including starting, pausing, and stopping tracking.
// It handles location updates, calculates running statistics like speed and distance, and maintains the current state of the run.
@Singleton
class TrackingManager @Inject constructor(
    private val locationTrackingManager: LocationTrackingManager,
    private val timeTracker: TimeTracker,
    private val trackingServiceManager: TrackingServiceManager
) {
    private var isTracking = false
        set(value) {
            _currentRunState.update { it.copy(isTracking = value) }
            field = value
        }

    private val _currentRunState = MutableStateFlow(CurrentRunState())
    val currentRunState = _currentRunState

    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    private val _trackingDurationInMs = MutableStateFlow(0L)
    val trackingDurationInMs = _trackingDurationInMs.asStateFlow()

    private val timeTrackerCallback = { timeElapsed: Long ->
        _trackingDurationInMs.update { timeElapsed }
    }

    private var isFirst = true

    // Location callback to get location updates
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if (isTracking) {
                result.locations.lastOrNull()?.let { location ->
                    addPathPoints(location)
                    _currentLocation.value = LatLng(location.latitude, location.longitude)
                }
            }
        }
    }


    //initializes the start state of the run
    private fun postInitialValue() {
        _currentRunState.update {
            CurrentRunState()
        }
        _trackingDurationInMs.update { 0 }
    }

    private fun addPathPoints(location: Location?) = location?.let {
        val pos = LatLng(it.latitude, it.longitude)
        _currentRunState.update { state ->
            val pathPoints = state.pathPoints + PathPoint.LocationPoint(pos)
            state.copy(
                pathPoints = pathPoints,
                distanceInMeters = state.distanceInMeters.run {
                    var distance = this
                    if (pathPoints.size > 1)
                        distance += LocationUtils.getDistanceBetweenPathPoints(
                            pathPoint1 = pathPoints[pathPoints.size - 1],
                            pathPoint2 = pathPoints[pathPoints.size - 2]
                        )
                    distance
                },
                speedInKMH = (it.speed * 3.6f).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_UP).toFloat()
            )
        }
    }

    fun startResumeTracking() {
        if (isTracking)
            return
        if (isFirst) {
            postInitialValue()
            trackingServiceManager.startService()
            isFirst = false
        }
        isTracking = true
        timeTracker.startResumeTimer(timeTrackerCallback)
        locationTrackingManager.registerCallback(locationCallback)
    }

    private fun addEmptyPolyLine() {
        _currentRunState.update {
            it.copy(
                pathPoints = it.pathPoints + PathPoint.EmptyLocationPoint
            )
        }
    }

    fun pauseTracking() {
        isTracking = false
        locationTrackingManager.unRegisterCallback(locationCallback)
        timeTracker.pauseTimer()
        addEmptyPolyLine()
    }

    fun stop() {
        pauseTracking()
        trackingServiceManager.stopService()
        timeTracker.stopTimer()
        postInitialValue()
        isFirst = true
    }

}