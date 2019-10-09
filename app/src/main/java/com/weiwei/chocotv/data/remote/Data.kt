package com.weiwei.chocotv.data.remote

import com.google.gson.annotations.SerializedName

data class Data<T> (
    @SerializedName("data")  var data: T? = null)