package com.oscarvj.pruebanach.di.module

import com.oscarvj.pruebanach.data.network.ApiClient
import com.oscarvj.pruebanach.di.manager.RetrofitManager
import com.oscarvj.pruebanach.retrofit.ClientRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val clientRetrofit: ClientRetrofit = ClientRetrofit()

    @Singleton
    @Provides
    fun provideRetrofit(): RetrofitManager {
        return RetrofitManager(clientRetrofit.getClient().create(ApiClient::class.java))
    }
}