package com.example.runyclub.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE username = :username")
    fun findUserByUsername(username: String): UserEntity?

    @Update
    fun updateUser(user: UserEntity)
}
