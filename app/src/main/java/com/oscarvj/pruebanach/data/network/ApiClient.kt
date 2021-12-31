package com.oscarvj.pruebanach.data.network

import com.oscarvj.pruebanach.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {

    @GET
    suspend fun getMovies(@Url uri: String): Response<ResponseModel>?
}