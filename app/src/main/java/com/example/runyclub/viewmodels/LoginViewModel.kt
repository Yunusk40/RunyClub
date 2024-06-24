package com.example.runyclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runyclub.roomdatabase.UserEntity
import com.example.runyclub.roomdatabase.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun registerUser(username: String, password: String, weight: Float, height: Float) {
        viewModelScope.launch {
            val user = UserEntity(username = username, password = password, weight = weight, height = height)
            userRepository.insertUser(user)
        }
    }

    fun loginUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.findUserByUsername(username)
            callback(user?.password == password)
        }
    }
}
