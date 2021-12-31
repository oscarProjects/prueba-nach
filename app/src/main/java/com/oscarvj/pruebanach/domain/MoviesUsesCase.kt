package com.oscarvj.pruebanach.domain

import com.oscarvj.pruebanach.data.model.ResponseModel
import com.oscarvj.pruebanach.data.repository.DataRepository
import javax.inject.Inject

class MoviesUsesCase @Inject constructor(
    private val repository: DataRepository
){
    suspend operator fun invoke() : ResponseModel? = repository.getAllMovies()
}