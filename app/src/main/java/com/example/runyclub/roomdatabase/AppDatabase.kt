package com.example.runyclub.roomdatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.example.runyclub.roomdatabase.dao.RunDao
import com.example.runyclub.roomdatabase.Converter
import com.example.runyclub.roomdatabase.dao.UserDao
import com.example.runyclub.roomdatabase.entity.RunEntity
import com.example.runyclub.roomdatabase.entity.UserEntity

@Database(entities = [UserEntity::class, RunEntity::class], version = 2)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun runDao(): RunDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Allow for destructive migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}