package com.example.ozhinshe.sign

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.HomeActivity
import com.example.ozhinshe.R
import com.example.ozhinshe.SearchFragment
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.modiedata.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class SplashScreen : AppCompatActivity() {

    private lateinit var mainApi: MainApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        initRetrofit()
        loadLocale()

        val sharedPreferences = getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)

        if(token == null){
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        }
        else if(token == "loged out"){
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_onBoardFragment_to_authorizationFragment)
        }
        else{
            login()
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_onBoardFragment_to_authorizationFragment)
        }
    }
    private fun login(){
        val sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        auth(AuthRequest(email.toString(), password.toString())) { success ->
            if (success) {
                val intent = Intent(this@SplashScreen, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun auth(authRequest: AuthRequest, onComplete: (Boolean) -> Unit){
        lifecycleScope.launch(Dispatchers.IO){
            val responce = mainApi.auth(authRequest)
            lifecycleScope.launch(Dispatchers.Main) {
                if(responce.errorBody() != null){
                    onComplete(false)
                }
                else{
                    onComplete(true)
                }
                val user = responce.body()
                val sharedPreferences = getSharedPreferences("Authotification", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("token_key", user?.accessToken)
                editor.apply()
            }
        }
    }
    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).
        client(client).addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null && language.isNotEmpty()) {
            setLocale(language)
        }
    }
    private fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}