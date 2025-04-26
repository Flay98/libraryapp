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
import androidx.fragment.app.viewModels

class RegisterFragment: Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerButton = view.findViewById<Button>(R.id.btnRegisterConfirm)

        val emailInput = view.findViewById<EditText>(R.id.enter_login)
        val passwordInput = view.findViewById<EditText>(R.id.enter_password)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                viewModel.register(email, password,
                    onSuccess = {
                        Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    },
                    onFailure = { errorMessage ->
                        Toast.makeText(context, "Ошибка: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                )
            } else {
                Toast.makeText(context, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton = view.findViewById<Button>(R.id.button_back_to_login)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}