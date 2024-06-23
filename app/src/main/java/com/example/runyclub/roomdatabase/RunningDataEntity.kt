package com.example.runyclub.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "running_data")
data class RunningDataEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    //@ColumnInfo(name = "img") @TypeConverters(BitmapConverter::class) var img: Bitmap? = null,
    @ColumnInfo(name = "timestamp") var timestamp: Long = 0L,
    @ColumnInfo(name = "avg_speed_in_kmh") var avgSpeedInKMH: Float = 0f,
    @ColumnInfo(name = "distance_in_meters") var distanceInMeters: Int = 0,
    @ColumnInfo(name = "time_in_millis") var timeInMillis: Long = 0L,
    @ColumnInfo(name = "calories_burned") var caloriesBurned: Int = 0
)