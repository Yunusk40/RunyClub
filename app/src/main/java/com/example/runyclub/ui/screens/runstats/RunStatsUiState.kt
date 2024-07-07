package com.example.runyclub.ui.screens.runstats

import androidx.compose.runtime.Immutable
import com.example.runyclub.common.extension.setDateToWeekFirstDay
import com.example.runyclub.common.extension.setDateToWeekLastDay
import com.example.runyclub.common.extension.setMinimumTime
import com.example.runyclub.common.extension.toCalendar
import com.example.runyclub.database.model.Run
import java.util.Calendar
import java.util.Date

@Immutable
data class RunStatsUiState(
    val dateRange: ClosedRange<Date>,
    val runStats: List<Run>,
    val statisticToShow: Statistic,
    val runStatisticsOnDate: Map<Date, AccumulatedRunStatisticsOnDate>,
) {

    data class AccumulatedRunStatisticsOnDate(
        val date: Date = Date(),
        val distanceInMeters: Int = 0,
        val durationInMillis: Long = 0L,
        val caloriesBurned: Int = 0
    ) {
        operator fun plus(other: AccumulatedRunStatisticsOnDate?) = other?.let {
            AccumulatedRunStatisticsOnDate(
                date = this.date,
                distanceInMeters = this.distanceInMeters + other.distanceInMeters,
                durationInMillis = this.durationInMillis + other.durationInMillis,
                caloriesBurned = this.caloriesBurned + other.caloriesBurned
            )
        } ?: this

        companion object {
            fun fromRun(run: Run) = AccumulatedRunStatisticsOnDate(
                date = run.timestamp.toCalendar().setMinimumTime().time,
                distanceInMeters = run.distanceInMeters,
                durationInMillis = run.durationInMillis,
                caloriesBurned = run.caloriesBurned
            )
        }
    }

    enum class Statistic {
        CALORIES,
        DURATION,
        DISTANCE
    }

    companion object {
        val EMPTY_STATE
            get() = RunStatsUiState(
                dateRange = Calendar.getInstance().setDateToWeekFirstDay().time..
                        Calendar.getInstance().setDateToWeekLastDay().time,
                runStats = emptyList(),
                statisticToShow = Statistic.DISTANCE,
                runStatisticsOnDate = emptyMap()
            )
    }
}
