package com.example.libraryapp.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun register(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: return@addOnSuccessListener

                val userData = hashMapOf(
                    "email" to email,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "darkThemeEnabled" to false
                )

                db.collection("users").document(userId).set(userData)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it.message ?: "Ошибка записи в БД") }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Ошибка регистрации")
            }
    }
}