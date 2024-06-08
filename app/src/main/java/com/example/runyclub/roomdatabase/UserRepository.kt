package com.example.runyclub.roomdatabase

import User
import UserDao

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun findUserByUsername(username: String) = userDao.findUserByUsername(username)
}
