package com.example.runyclub.roomdatabase

import com.example.runyclub.roomdatabase.RunningDataEntity
import com.example.runyclub.roomdatabase.RunningDataDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunningDataDao
) {
    suspend fun insertRun(run: RunningDataEntity) = runDao.insertRun(run)

    suspend fun deleteRun(run: RunningDataEntity) = runDao.deleteRun(run)

    fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    //fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    //fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    //fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    //fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    //fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    //fun getTotalDistance() = runDao.getTotalDistance()

    //fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    //fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()
}