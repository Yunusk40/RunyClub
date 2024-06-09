package com.example.runyclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runyclub.roomdatabase.UserEntity
import com.example.runyclub.roomdatabase.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun registerUser(username: String, password: String) = viewModelScope.launch {
        val user = UserEntity(username = username, password = password)
        repository.insertUser(user)
    }

    fun loginUser(username: String, password: String, action: (Boolean) -> Unit) = viewModelScope.launch {
        val user = repository.findUserByUsername(username)
        if (user != null && user.password == password) {
            action(true)
        } else {
            action(false)
        }
    }
}
