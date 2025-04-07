package com.example.libraryapp.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccountViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _readCount = MutableLiveData<Int>()
    val readCount: LiveData<Int> get() = _readCount

    init {
        loadUserData()
    }

    private fun loadUserData() {
        // Пока что: заглушки
        _username.value = "user123"
        _readCount.value = 12
    }

    fun logout() {
        // В будущем можно очистить токен, сбросить данные и т.п.
    }
}