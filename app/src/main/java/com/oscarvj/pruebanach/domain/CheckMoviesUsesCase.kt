package com.oscarvj.pruebanach.domain

import com.oscarvj.pruebanach.data.repository.DataBaseRepository
import javax.inject.Inject

class CheckMoviesUsesCase @Inject constructor(private val dataBaseRepository: DataBaseRepository) {

    suspend operator fun invoke(): Int? = dataBaseRepository.countValues()
}