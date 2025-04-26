package com.example.libraryapp.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.R
import androidx.fragment.app.viewModels

class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    private lateinit var loginText: TextView
    private lateinit var readCountText: TextView
    private lateinit var themeSwitch: Switch
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginText = view.findViewById(R.id.loginText)
        readCountText = view.findViewById(R.id.readCountText)
        themeSwitch = view.findViewById(R.id.themeSwitch)
        logoutButton = view.findViewById(R.id.logoutButton)

        viewModel.username.observe(viewLifecycleOwner) { login ->
            loginText.text = "Логин: $login"
        }

        viewModel.readCount.observe(viewLifecycleOwner) { count ->
            readCountText.text = "Прочитано книг: $count"
        }
        viewModel.loadUserTheme()

        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            themeSwitch.isChecked = isDark
        }

        themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                viewModel.updateUserTheme(isChecked)
                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        logoutButton.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.navigation_login)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}