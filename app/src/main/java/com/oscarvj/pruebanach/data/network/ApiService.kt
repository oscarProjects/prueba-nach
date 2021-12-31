package com.oscarvj.pruebanach.data.network

import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.data.model.ResponseModel
import com.oscarvj.pruebanach.di.manager.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(
    private val serviceManager: RetrofitManager
) {

    suspend fun getMovies() : ResponseModel {
        return withContext(Dispatchers.IO){
            val response = serviceManager.callServiceGetMovies(Constants.URL_METHOD)
            response?.body()!!
        }
    }
}