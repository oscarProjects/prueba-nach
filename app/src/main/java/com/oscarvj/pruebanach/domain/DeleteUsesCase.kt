package com.oscarvj.pruebanach.domain

import com.oscarvj.pruebanach.data.repository.DataBaseRepository
import javax.inject.Inject

class DeleteUsesCase @Inject constructor(private val dataBaseRepository: DataBaseRepository) {

    suspend operator fun invoke() = dataBaseRepository.deleteMovies()
}