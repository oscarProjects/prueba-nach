package com.oscarvj.pruebanach.domain

import com.oscarvj.pruebanach.data.database.model.MovieEntity
import com.oscarvj.pruebanach.data.repository.DataBaseRepository
import javax.inject.Inject

class SaveMoviesUsesCase @Inject constructor(private val dataBaseRepository: DataBaseRepository) {

    suspend fun saveMovie(movieEntity: MovieEntity){
        dataBaseRepository.saveMovie(movieEntity)
    }
}