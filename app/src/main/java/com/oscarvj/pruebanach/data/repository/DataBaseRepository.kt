package com.oscarvj.pruebanach.data.repository

import com.oscarvj.pruebanach.data.database.dao.MovieDao
import com.oscarvj.pruebanach.data.database.model.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataBaseRepository @Inject constructor(private val movieDao: MovieDao) {

    suspend fun saveMovie(movieEntity: MovieEntity){
        withContext(Dispatchers.IO){
            movieDao.insert(movieEntity)
        }
    }

    val allMovies: Flow<List<MovieEntity>> = movieDao.getMovies()

    suspend fun deleteMovies(){
        withContext(Dispatchers.IO){
            movieDao.deleteAll()
        }
    }

    suspend fun countValues(): Int? {
        return movieDao.getCount()
    }

}