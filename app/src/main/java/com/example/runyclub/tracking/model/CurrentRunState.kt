package com.example.runyclub.core.tracking.model

import com.example.runyclub.tracking.model.PathPoint

data class CurrentRunState(
    val distanceInMeters: Int = 0,
    val speedInKMH: Float = 0f,
    val isTracking: Boolean = false,
    val pathPoints: List<PathPoint> = emptyList()
)
