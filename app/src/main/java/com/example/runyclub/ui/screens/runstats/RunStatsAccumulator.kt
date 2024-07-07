package com.example.runyclub.ui.screens.runstats

import com.example.runyclub.database.model.Run
import com.example.runyclub.ui.screens.runstats.RunStatsUiState.AccumulatedRunStatisticsOnDate
import java.util.Date

object RunStatsAccumulator {

    fun accumulateRunByDate(
        list: List<Run>
    ): Map<Date, AccumulatedRunStatisticsOnDate> {
        return buildMap {
            list.forEach { run ->
                val newStats = AccumulatedRunStatisticsOnDate.fromRun(run)
                this[newStats.date] = newStats + this[newStats.date]
            }
        }
    }

}