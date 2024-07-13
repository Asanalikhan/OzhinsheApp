package com.example.ozhinshe.domain.utils

import android.app.Application
import android.content.Context
import com.example.ozhinshe.domain.data.ServiceBuilder

public class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceBuilder.initialize(this)
    }
}
