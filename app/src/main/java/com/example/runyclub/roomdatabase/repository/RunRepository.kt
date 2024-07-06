package com.sdevprem.runtrack.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.runyclub.roomdatabase.RunSortOrder
import com.example.runyclub.roomdatabase.entity.RunEntity
import com.example.runyclub.roomdatabase.dao.RunDao
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RunRepository @Inject constructor(
    private val runDao: RunDao
) {
    suspend fun insertRun(run: RunEntity) = runDao.insertRun(run)

    suspend fun deleteRun(run: RunEntity) = runDao.deleteRun(run)

    fun getSortedAllRun(sortingOrder: RunSortOrder) = Pager(
        config = PagingConfig(pageSize = 20),
    ) {
        when (sortingOrder) {
            RunSortOrder.DATE -> runDao.getAllRunSortByDate()
            RunSortOrder.DURATION -> runDao.getAllRunSortByDuration()
            RunSortOrder.CALORIES_BURNED -> runDao.getAllRunSortByCaloriesBurned()
            RunSortOrder.AVG_SPEED -> runDao.getAllRunSortByAvgSpeed()
            RunSortOrder.DISTANCE -> runDao.getAllRunSortByDistance()
        }
    }

    suspend fun getRunStatsInDateRange(fromDate: Date?, toDate: Date?) =
        runDao.getRunStatsInDateRange(fromDate, toDate)

    fun getRunByDescDateWithLimit(limit: Int) = runDao.getRunByDescDateWithLimit(limit)

    fun getTotalRunningDuration(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalRunningDuration(fromDate, toDate)

    fun getTotalCaloriesBurned(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalCaloriesBurned(fromDate, toDate)

    fun getTotalDistance(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalDistance(fromDate, toDate)

    fun getTotalAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Float> =
        runDao.getTotalAvgSpeed(fromDate, toDate)

}