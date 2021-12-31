package com.oscarvj.pruebanach.domain

import com.oscarvj.pruebanach.data.repository.DataBaseRepository
import javax.inject.Inject

class MoviesLocalUsesCase @Inject constructor(private val dataBaseRepository: DataBaseRepository) {

    operator fun invoke() = dataBaseRepository.allMovies
}