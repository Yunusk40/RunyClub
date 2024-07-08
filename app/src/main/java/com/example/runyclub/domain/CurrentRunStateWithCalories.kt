package com.example.runyclub.domain

import com.example.runyclub.tracking.model.CurrentRunState

//hold information about the current state of a run along with the calories burnt
data class CurrentRunStateWithCalories(
    val currentRunState: CurrentRunState = CurrentRunState(),
    val caloriesBurnt: Int = 0
)