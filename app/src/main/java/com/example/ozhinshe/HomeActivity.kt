package com.example.ozhinshe

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ozhinshe.databinding.ActivityHomeBinding
import com.example.ozhinshe.profile.ProfileFragment
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView3) as NavHostFragment
        navController = navHostFragment.navController

        setTheme()
        setLanguage()

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_ -> navController.navigate(R.id.homeFragment)
                R.id.search -> navController.navigate(R.id.searchFragment)
                R.id.bookmark -> navController.navigate(R.id.bookmarkFragment)
                R.id.profile -> navController.navigate(R.id.profileFragment)
            }
            true
        }
    }

    fun hideBottomNavigationView() {
        binding.bottomNavigationView.visibility = View.GONE
    }
    fun showBottomNavigationView() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
    private fun setTheme(){
        val sharedPref = getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val isNightMode = sharedPref.getBoolean("isDarkTheme", false)
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    private fun setLanguage(){
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocale(language.toString())
    }
    private fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("My_Lang", localeName)
            apply()
        }
    }

}