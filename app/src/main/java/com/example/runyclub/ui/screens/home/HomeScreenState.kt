package com.example.runyclub.ui.screens.home

import com.example.runyclub.database.model.Run
import com.example.runyclub.database.model.User
import com.example.runyclub.domain.CurrentRunStateWithCalories

data class HomeScreenState(
    val runList: List<Run> = emptyList(),
    val currentRunStateWithCalories: CurrentRunStateWithCalories = CurrentRunStateWithCalories(),
    val currentRunInfo: Run? = null,
    val user: User = User(),
    val distanceCoveredInKmInThisWeek: Float = 0.0f
)
