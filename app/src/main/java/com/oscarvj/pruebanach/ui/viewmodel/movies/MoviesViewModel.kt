package com.oscarvj.pruebanach.ui.viewmodel.movies

import android.graphics.Movie
import androidx.lifecycle.*
import com.oscarvj.pruebanach.data.database.model.MovieEntity
import com.oscarvj.pruebanach.data.model.ResponseModel
import com.oscarvj.pruebanach.domain.DeleteUsesCase
import com.oscarvj.pruebanach.domain.MoviesLocalUsesCase
import com.oscarvj.pruebanach.domain.MoviesUsesCase
import com.oscarvj.pruebanach.domain.SaveMoviesUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUsesCase: MoviesUsesCase,
    private val saveMoviesUsesCase: SaveMoviesUsesCase,
    moviesLocalUsesCase: MoviesLocalUsesCase,
    private val deleteUsesCase: DeleteUsesCase
) : ViewModel() {

    val moviesModel = MutableLiveData<ResponseModel?>()

    val isLoading = MutableLiveData<Boolean>()

    fun getAllMovies(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = moviesUsesCase()
            if(result != null){
                moviesModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun saveMovie(movieEntity: MovieEntity){
        viewModelScope.launch {
            saveMoviesUsesCase.saveMovie(movieEntity)
        }
    }

    val allMovies: LiveData<List<MovieEntity>> = moviesLocalUsesCase().asLiveData()

    fun deleteMovies(){
        viewModelScope.launch {
            deleteUsesCase()
        }
    }
}