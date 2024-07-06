package com.example.runyclub.roomdatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "running_data")
data class RunEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    //@ColumnInfo(name = "img") @TypeConverters(BitmapConverter::class) var img: Bitmap? = null,
    @ColumnInfo(name = "timestamp") var timestamp: Date = Date(),
    @ColumnInfo(name = "avg_speed_in_kmh") var avgSpeedInKMH: Float = 0f,
    @ColumnInfo(name = "distance_in_meters") var distanceInMeters: Int = 0,
    @ColumnInfo(name = "time_in_millis") var timeInMillis: Long = 0L,
    @ColumnInfo(name = "calories_burned") var caloriesBurned: Int = 0
)