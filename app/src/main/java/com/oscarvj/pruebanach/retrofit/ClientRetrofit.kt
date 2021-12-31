package com.oscarvj.pruebanach.retrofit

import com.oscarvj.pruebanach.BuildConfig
import com.oscarvj.pruebanach.constants.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofit {
    private val httpClient = OkHttpClient.Builder()
    lateinit var client: OkHttpClient

    fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        httpClient.addInterceptor(interceptor)

        client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}