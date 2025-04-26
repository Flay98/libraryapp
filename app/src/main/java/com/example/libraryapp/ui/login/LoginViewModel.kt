package com.example.libraryapp.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){
    private val auth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Неизвестная ошибка")
            }
    }
}