package com.oscarvj.pruebanach.data.database.dao

import androidx.room.*
import com.oscarvj.pruebanach.data.database.model.MovieEntity
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.LiveData

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies ORDER BY `originalTitle` ASC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getCount(): Int?
}