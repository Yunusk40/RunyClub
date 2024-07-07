package com.example.runyclub.ui.screens.login

import com.example.runyclub.database.model.Gender

interface LoginScreenEvent {
    fun updateName(name: String)
    fun updateGender(gender: Gender)
    fun updateWeight(weightInKg: Float)
    fun updateWeeklyGoal(weeklyGoalInKm: Float)
}