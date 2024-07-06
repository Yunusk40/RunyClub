package com.example.runyclub.domain.usecase

import com.example.runyclub.common.utils.RunUtils
import com.example.runyclub.roomdatabase.repository.UserRepository
import com.example.runyclub.tracking.TrackingManager
import com.example.runyclub.domain.model.CurrentRunStateWithCalories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

//encapsulate the logic for fetching the current state of a run
@Singleton
class GetCurrentRunStateWithCaloriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val trackingManager: TrackingManager
) {
    operator fun invoke(username: String): Flow<CurrentRunStateWithCalories> {
        return combine(userRepository.getUserFlow(username), trackingManager.currentRunState) { user, runState ->
            if (user != null) {
                CurrentRunStateWithCalories(
                    currentRunState = runState,
                    caloriesBurnt = RunUtils.calculateCaloriesBurnt(
                        distanceInMeters = runState.distanceInMeters,
                        weightInKg = user.weight
                    ).roundToInt()
                )
            } else {
                CurrentRunStateWithCalories(currentRunState = runState)
            }
        }
    }
}