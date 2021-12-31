package com.oscarvj.pruebanach.di.manager

import com.oscarvj.pruebanach.data.model.ResponseModel
import com.oscarvj.pruebanach.data.network.ApiClient
import retrofit2.Response

class RetrofitManager(var api: ApiClient) {

    suspend fun callServiceGetMovies(service: String): Response<ResponseModel>? {
        return api.getMovies(service)
    }
}
