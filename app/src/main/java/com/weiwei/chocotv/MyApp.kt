package com.weiwei.chocotv

import android.app.Application
import com.weiwei.chocotv.util.InjectorUtils

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        InjectorUtils.initialize(this)
    }
}