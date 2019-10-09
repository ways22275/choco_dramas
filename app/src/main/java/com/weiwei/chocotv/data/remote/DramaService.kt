package com.weiwei.chocotv.data.remote

import com.weiwei.chocotv.data.DramaItem
import io.reactivex.Single
import retrofit2.http.GET

interface DramaService {

    @GET("v2/5a97c59c30000047005c1ed2")
    fun getDramaList() : Single<List<DramaItem>>
}