package com.example.runyclub.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Interface for data access operations, like querying and inserting user data
@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE username = :username")
    fun findUserByUsername(username: String): UserEntity?
}
