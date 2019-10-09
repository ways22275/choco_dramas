package com.weiwei.chocotv.data.remote

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResponseConverterFactory(gSon: Gson): Converter.Factory() {

    private val gSonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gSon)

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = Data::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? = gSonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return ResponseBodyConverter(gsonConverter as Converter<ResponseBody, Data<Any>>)
    }

    class ResponseBodyConverter<T>(private val converter: Converter<ResponseBody, Data<T>>) :
        Converter<ResponseBody, T> {

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): T {
            val response = converter.convert(responseBody)
            return response?.data!!
        }
    }
}