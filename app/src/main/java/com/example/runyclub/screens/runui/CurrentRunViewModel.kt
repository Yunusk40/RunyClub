package com.example.runyclub.screens.runui

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runyclub.tracking.TrackingManager
import com.example.runyclub.di.ApplicationScope
import com.example.runyclub.di.IoDispatcher
import com.example.runyclub.domain.model.CurrentRunStateWithCalories
import com.example.runyclub.domain.usecase.GetCurrentRunStateWithCaloriesUseCase
import com.example.runyclub.roomdatabase.entity.RunEntity
import com.sdevprem.runtrack.core.data.repository.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CurrentRunViewModel @Inject constructor(
    private val trackingManager: TrackingManager,
    private val repository: RunRepository,
    @ApplicationScope
    private val appCoroutineScope: CoroutineScope,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    getCurrentRunStateWithCaloriesUseCase: GetCurrentRunStateWithCaloriesUseCase
) : ViewModel() {
    val currentRunStateWithCalories = getCurrentRunStateWithCaloriesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CurrentRunStateWithCalories()
        )
    val runningDurationInMillis = trackingManager.trackingDurationInMs

    fun playPauseTracking() {
        if (currentRunStateWithCalories.value.currentRunState.isTracking)
            trackingManager.pauseTracking()
        else trackingManager.startResumeTracking()
    }

    fun finishRun(bitmap: Bitmap) {
        trackingManager.pauseTracking()
        saveRun(
            RunEntity(
                avgSpeedInKMH = currentRunStateWithCalories.value.currentRunState.distanceInMeters
                    .toBigDecimal()
                    .multiply(3600.toBigDecimal())
                    .divide(runningDurationInMillis.value.toBigDecimal(), 2, RoundingMode.HALF_UP)
                    .toFloat(),
                distanceInMeters = currentRunStateWithCalories.value.currentRunState.distanceInMeters,
                timeInMillis = runningDurationInMillis.value,
                timestamp = Date(),
                caloriesBurned = currentRunStateWithCalories.value.caloriesBurnt
            )
        )
        trackingManager.stop()
    }

    private fun saveRun(run: RunEntity) = appCoroutineScope.launch(ioDispatcher) {
        repository.insertRun(run)
    }

}