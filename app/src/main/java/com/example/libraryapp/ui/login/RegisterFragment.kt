package com.example.libraryapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerButton = view.findViewById<Button>(R.id.btnRegisterConfirm)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val emailInput = view.findViewById<EditText>(R.id.enter_login)
        val passwordInput = view.findViewById<EditText>(R.id.enter_password)

        registerButton.setOnClickListener {
            // Переход на главный экран
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = task.result?.user?.uid
                            val userData = hashMapOf(
                                "email" to email,
                                "createdAt" to FieldValue.serverTimestamp()
                            )

                            if (userId != null) {
                                db.collection("users").document(userId).set(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Ошибка при сохранении в БД", Toast.LENGTH_LONG).show()
                                    }
                            }
                        } else {
                            Toast.makeText(context, "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton = view.findViewById<Button>(R.id.button_back_to_login)

        backButton.setOnClickListener {
            // Переход на главный экран
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}