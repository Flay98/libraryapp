package com.example.libraryapp.ui.account

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountViewModel : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username


    private val _readCount = MutableLiveData<Int>()
    val readCount: LiveData<Int> get() = _readCount

    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    init {
        loadUserData()
    }

    fun loadUserData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        _username.value = FirebaseAuth.getInstance().currentUser?.email?.substringBefore("@") ?: "User"

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("books")
            .whereEqualTo("status", "прочитано")
            .get()
            .addOnSuccessListener { snapshot ->
                _readCount.value = snapshot.size()
            }
            .addOnFailureListener {
                Log.e("AccountViewModel", "Ошибка загрузки количества книг", it)
            }


    }

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun loadUserTheme() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val isDark = snapshot.getBoolean("darkThemeEnabled") ?: false
                _isDarkTheme.value = isDark
                AppCompatDelegate.setDefaultNightMode(
                    if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
    }

    fun updateUserTheme(isDark: Boolean) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .update("darkThemeEnabled", isDark)
            .addOnSuccessListener {
                _isDarkTheme.value = isDark
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _username.value = ""
        _readCount.value = 0
    }

    fun refresh(){
        loadUserData()
    }
}