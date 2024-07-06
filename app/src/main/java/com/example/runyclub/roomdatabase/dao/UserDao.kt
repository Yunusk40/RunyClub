package com.example.runyclub.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.runyclub.roomdatabase.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE username = :username")
    fun findUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun findUserByUsernameFlow(username: String): Flow<UserEntity?>

    @Update
    fun updateUser(user: UserEntity)
}
