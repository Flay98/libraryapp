package com.example.libraryapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.R
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

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
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                viewModel.login(email, password,
                    onSuccess = {
                        Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show()
                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                        if (userId != null) {
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(userId)
                                .get()
                                .addOnSuccessListener { snapshot ->
                                    val isDarkTheme = snapshot.getBoolean("darkThemeEnabled") ?: false
                                    AppCompatDelegate.setDefaultNightMode(
                                        if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
                                        else AppCompatDelegate.MODE_NIGHT_NO
                                    )
                                    findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
                                    requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_home
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Ошибка входа: ${it.message}", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
                            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId = R.id.navigation_home
                        }

                    },
                    onFailure = { errorMessage ->
                        Toast.makeText(context, "Ошибка: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }

        val registerButton = view.findViewById<Button>(R.id.btnRegister)

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}