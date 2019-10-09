package com.weiwei.chocotv.util.sharePreference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.weiwei.chocotv.util.Constant.SharedKeyNetwork
import com.weiwei.chocotv.util.Constant.SharedKeyWordKey
import com.weiwei.chocotv.util.Constant.SharedPreferenceKey

object SharePreferenceManager {

    private var sharedPreferences: SharedPreferences? = null

    fun initSharePreferences (application: Application){
        sharedPreferences = application.getSharedPreferences(SharedPreferenceKey, Context.MODE_PRIVATE)
    }

    fun putKeyWord(string : String) {
        sharedPreferences?.edit()?.putString(SharedKeyWordKey, string)?.apply()
    }

    fun getKeyWord() : String? {
        return sharedPreferences?.getString(SharedKeyWordKey, "")
    }

    fun putNetworkEnable(enable : Boolean) {
        sharedPreferences?.edit()?.putBoolean(SharedKeyNetwork, enable)?.apply()
    }

    fun getNetworkEnable() : Boolean? {
        return sharedPreferences?.getBoolean(SharedKeyNetwork, false)
    }
}