package com.example.runyclub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.runyclub.database.dao.RunDao
import com.example.runyclub.database.model.Run

@Database(
    entities = [Run::class],
    version = 1,
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val RUN_TRACK_DB_NAME = "run_track_db"
    }

    abstract fun getRunDao(): RunDao

}