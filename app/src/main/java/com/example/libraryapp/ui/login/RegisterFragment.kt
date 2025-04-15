package com.example.libraryapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.R

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

        registerButton.setOnClickListener {
            // Переход на главный экран
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        val backButton = view.findViewById<Button>(R.id.button_back_to_login)

        backButton.setOnClickListener {
            // Переход на главный экран
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}