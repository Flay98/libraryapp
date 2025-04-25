package com.example.libraryapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentHomeBinding
import com.example.libraryapp.databinding.FragmentLoginBinding
import com.example.libraryapp.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput = view.findViewById<EditText>(R.id.login_form)
        val passwordInput = view.findViewById<EditText>(R.id.password_form)
        val loginButton = view.findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener {
            // Переход на главный экран
            //findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_navigation_login_to_navigation_home) // или home
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Ошибка входа: ${it.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }

        val registerButton = view.findViewById<Button>(R.id.btnRegister)

        registerButton.setOnClickListener {
            // Переход на главный экран
            findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
            //_binding = null
    }

}