package com.example.libraryapp

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.libraryapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.navigation_login,
                R.id.navigation_register-> {
                    // На экране входа/регистрации скрываем нижнюю панель
                    navView.visibility = View.GONE
                }
                else -> {
                    // На остальных экранах показываем
                    navView.visibility = View.VISIBLE
                }
            }
        }



        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_login, R.id.navigation_home, R.id.navigation_catalog, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (!navController.popBackStack(R.id.navigation_home, false)) {
                        navController.navigate(R.id.navigation_home)
                    }
                    true
                }
                R.id.navigation_catalog -> {
                    if (!navController.popBackStack(R.id.navigation_catalog, false)) {
                        navController.navigate(R.id.navigation_catalog)
                    }
                    true
                }
                R.id.navigation_settings -> {
                    if (!navController.popBackStack(R.id.navigation_settings, false)) {
                        navController.navigate(R.id.navigation_settings)
                    }
                    true
                }
                else -> false
            }
        }

    }
}