package com.example.runyclub.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runyclub.roomdatabase.entity.UserEntity
import com.example.runyclub.roomdatabase.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> get() = _user

    fun loadUser(username: String) {
        viewModelScope.launch {
            _user.value = userRepository.findUserByUsername(username)
        }
    }

    fun updateUser(username: String, weight: Float, height: Float) {
        viewModelScope.launch {
            val user = _user.value?.copy(weight = weight, height = height)
            if (user != null) {
                userRepository.updateUser(user)
                _user.value = user
            }
        }
    }
}
