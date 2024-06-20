package com.example.ozhinshe.sign

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.HomeActivity
import com.example.ozhinshe.R
import com.example.ozhinshe.SearchFragment

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)

        if(token == null){
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        }
        else{
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_onBoardFragment_to_authorizationFragment)
//            val intent = Intent(this@SplashScreen, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
        }

    }
}