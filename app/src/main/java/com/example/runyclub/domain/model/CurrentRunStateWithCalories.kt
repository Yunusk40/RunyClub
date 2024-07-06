package com.example.runyclub.domain.model

import com.example.runyclub.core.tracking.model.CurrentRunState

data class CurrentRunStateWithCalories(
    val currentRunState: CurrentRunState = CurrentRunState(),
    val caloriesBurnt: Int = 0
)