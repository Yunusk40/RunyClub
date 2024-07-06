package com.example.runyclub.roomdatabase.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runyclub.roomdatabase.entity.RunEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: RunEntity)

    @Delete
    suspend fun deleteRun(run: RunEntity)

    @Query("SELECT * FROM running_data ORDER BY timestamp DESC")
    fun getAllRunSortByDate(): PagingSource<Int, RunEntity>

    @Query("SELECT * FROM running_data ORDER BY time_in_millis DESC")
    fun getAllRunSortByDuration(): PagingSource<Int, RunEntity>

    @Query("SELECT * FROM running_data ORDER BY calories_burned DESC")
    fun getAllRunSortByCaloriesBurned(): PagingSource<Int, RunEntity>

    @Query("SELECT * FROM running_data ORDER BY avg_speed_in_kmh DESC")
    fun getAllRunSortByAvgSpeed(): PagingSource<Int, RunEntity>

    @Query("SELECT * FROM running_data ORDER BY distance_in_meters DESC")
    fun getAllRunSortByDistance(): PagingSource<Int, RunEntity>

    @Query("SELECT * FROM running_data ORDER BY timestamp DESC LIMIT :limit")
    fun getRunByDescDateWithLimit(limit: Int): Flow<List<RunEntity>>

    @Query(
        "SELECT * FROM running_data WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    suspend fun getRunStatsInDateRange(fromDate: Date?, toDate: Date?): List<RunEntity>


    //for statistics
    @Query(
        "SELECT TOTAL(time_in_millis) FROM running_data WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalRunningDuration(fromDate: Date?, toDate: Date?): Flow<Long>

    @Query(
        "SELECT TOTAL(calories_burned) FROM running_data WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalCaloriesBurned(fromDate: Date?, toDate: Date?): Flow<Long>

    @Query(
        "SELECT TOTAL(distance_in_meters) FROM running_data WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalDistance(fromDate: Date?, toDate: Date?): Flow<Long>

    @Query(
        "SELECT AVG(avg_speed_in_kmh) FROM running_data WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalAvgSpeed(fromDate: Date?, toDate: Date?): Flow<Float>

}