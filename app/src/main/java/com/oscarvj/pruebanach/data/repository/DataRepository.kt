package com.oscarvj.pruebanach.data.repository

import com.oscarvj.pruebanach.data.model.ResponseModel
import com.oscarvj.pruebanach.data.network.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val api: ApiService
){

    suspend fun getAllMovies(): ResponseModel {
        return api.getMovies()
    }
}