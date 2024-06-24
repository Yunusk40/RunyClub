package com.example.runyclub.roomdatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

//Setup for the Room database that includes the com.example.runyclub.roomdatabase.User entity and provides an instance of com.example.runyclub.roomdatabase.UserDao.
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
