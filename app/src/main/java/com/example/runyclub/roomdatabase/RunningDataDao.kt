package com.example.runyclub.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RunningDataDao {
    @Insert
    suspend fun insertRun(run: RunningDataEntity)

    @Delete
    suspend fun deleteRun(run: RunningDataEntity)

    @Query("SELECT * FROM running_data ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate(): List<RunningDataEntity>

    // Add other methods as needed
}