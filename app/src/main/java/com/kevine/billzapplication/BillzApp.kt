package com.kevine.billzapplication

import android.app.Application
import android.content.Context

class BillzApp:Application() {
    companion object{
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext= applicationContext
    }
}