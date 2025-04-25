package com.example.libraryapp.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.data.BookRepository
import com.google.firebase.auth.FirebaseAuth

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
        val email = FirebaseAuth.getInstance().currentUser?.email
        _username.value = email?.substringBefore("@")
        _readCount.value = BookRepository.getAllBooks()
            .count { it.status == "прочитано" }
        val count = BookRepository.getAllBooks().count { it.status == "прочитано" }
        println("🔍 Обновлённый счётчик: $count")
        _readCount.value = count
    }

    fun logout() {
        // В будущем можно очистить токен, сбросить данные и т.п.
    }

    fun refresh(){
        loadUserData()
    }
}