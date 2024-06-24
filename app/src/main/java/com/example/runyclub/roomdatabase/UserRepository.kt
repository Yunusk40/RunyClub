package com.example.runyclub.roomdatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//class provides an interface for interacting with the user data in the database. It uses a `UserDao` to perform database operations.
class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserEntity) = withContext(Dispatchers.IO) {
        userDao.insertUser(user)
    }

    suspend fun findUserByUsername(username: String) = withContext(Dispatchers.IO) {
        userDao.findUserByUsername(username)
    }

    suspend fun updateUser(user: UserEntity) = withContext(Dispatchers.IO) {
        userDao.updateUser(user)
    }
}
