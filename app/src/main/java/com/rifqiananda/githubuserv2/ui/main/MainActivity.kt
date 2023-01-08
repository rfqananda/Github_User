package com.rifqiananda.githubuserv2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rifqiananda.githubuserv2.R
import com.rifqiananda.githubuserv2.databinding.ActivityMainBinding
import com.rifqiananda.githubuserv2.ui.themes.ThemePreferences
import com.rifqiananda.githubuserv2.ui.themes.datastore
import com.rifqiananda.githubuserv2.ui.view.ThemeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : ThemeViewModel
        val preferences = ThemePreferences(datastore)
        val factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ThemeViewModel(preferences) as T
            }
        }

        viewModel = ViewModelProvider(this, factory)[ThemeViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNavigation.setupWithNavController(navController)

        viewModel.getSavedTheme {theme ->
            AppCompatDelegate.setDefaultNightMode(theme ?: AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

}