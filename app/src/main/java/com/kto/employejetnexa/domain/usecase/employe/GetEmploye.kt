package com.kto.employejetnexa.domain.usecase.employe

import com.kto.employejetnexa.domain.Repository
import javax.inject.Inject

class GetEmploye @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke() =
        repository.getEmployes() //operator sobre escribir funciones de la clase

}