package com.udacity.shoestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.User

class LoginViewModel : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun addUser(user: User): Boolean {
        val currentUsers = _users.value ?: emptyList()

        if (authenticate(user)) {
            return false
        }

        _users.value = currentUsers + user
        return true
    }

    fun authenticate(user: User): Boolean {
        _users.value?.forEach {
            if (it.username == user.username && it.password == user.password) {
                return true
            }
        }
        return false
    }

    init {
        _users.value = listOf(
            User("user@example.com", "password"),
            User("admin@example.com", "password")
        )
    }
}