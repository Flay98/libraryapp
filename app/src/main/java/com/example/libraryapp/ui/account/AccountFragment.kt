package com.example.libraryapp.ui.account

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentAccountBinding
import androidx.fragment.app.viewModels
import com.example.libraryapp.ui.home.HomeViewModel

class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    private lateinit var loginText: TextView
    private lateinit var readCountText: TextView
    private lateinit var themeSwitch: Switch
    private lateinit var logoutButton: Button
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginText = view.findViewById(R.id.loginText)
        readCountText = view.findViewById(R.id.readCountText)
        themeSwitch = view.findViewById(R.id.themeSwitch)
        logoutButton = view.findViewById(R.id.logoutButton)

        // Подписка на ViewModel
        viewModel.username.observe(viewLifecycleOwner) {
            loginText.text = "Логин: $it"
        }

        homeViewModel.readBooks.observe(viewLifecycleOwner) { books ->
            readCountText.text = "Прочитано книг: ${books.size}"
        }

        // Смена темы
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_theme", false)
        themeSwitch.isChecked = isDark

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_theme", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        logoutButton.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.navigation_login)
        }
    }
}