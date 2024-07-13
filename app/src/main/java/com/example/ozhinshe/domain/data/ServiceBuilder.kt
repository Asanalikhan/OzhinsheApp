package com.example.ozhinshe.domain.data

import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("Authotification", Context.MODE_PRIVATE)
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://api.ozinshe.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    fun <T> buildService(service: Class<T>): T = retrofit.create(service)

    fun getToken(): String? {
        return sharedPreferences.getString("token_key", null)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token_key", token).apply()
    }
}
