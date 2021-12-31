package com.oscarvj.pruebanach.ui.viewmodel.splash

import androidx.lifecycle.*
import com.oscarvj.pruebanach.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkMoviesUsesCase: CheckMoviesUsesCase,
) : ViewModel() {

    var countMovies = 0

    fun checkMovies(){
        viewModelScope.launch {
            val result = checkMoviesUsesCase()
            if (result != null) {
                countMovies = result
            }
        }
    }
}