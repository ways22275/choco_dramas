package com.weiwei.chocotv.util

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.weiwei.chocotv.BuildConfig
import com.weiwei.chocotv.data.DramaRepository
import com.weiwei.chocotv.data.local.DramaDao
import com.weiwei.chocotv.data.local.DramaDatabase
import com.weiwei.chocotv.data.remote.DramaService
import com.weiwei.chocotv.data.remote.ResponseConverterFactory
import com.weiwei.chocotv.util.sharePreference.SharePreferenceManager.initSharePreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class InjectorUtils{

    companion object {

        private var application : Application ? = null
        private var dramaModel : DramaDao? = null
        private var service : DramaService? = null

        fun initialize(appContext: Application) {
            application = appContext
            service = getService()
            dramaModel = getDatabase(appContext).dramaModel()
            initSharePreferences(appContext)
        }

        fun providerRepository() : DramaRepository? = service?.let { dramaModel?.let { it1 ->
            DramaRepository(
                it1, it)
        } }

        @Volatile
        private var InstanceService: DramaService? = null

        private fun getService(): DramaService =
            InstanceService ?: synchronized(this) {
                InstanceService ?: providerDramaService().also { InstanceService = it }
            }

        private fun providerDramaService() : DramaService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE))
                .retryOnConnectionFailure(true)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.Drama_Domain)
                .addConverterFactory(ResponseConverterFactory(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(DramaService::class.java)
        }

        @Volatile
        private var InstanceRoom : DramaDatabase? = null

        private fun getDatabase(application : Application) : DramaDatabase =
            InstanceRoom ?: synchronized(this) {
                InstanceRoom ?: providerDatabase(application).also { InstanceRoom = it }
            }

        private fun providerDatabase(context: Context): DramaDatabase {
            return Room
                .databaseBuilder<DramaDatabase>(context.applicationContext, DramaDatabase::class.java, "Drama.db")
                .build()
        }
    }
}

