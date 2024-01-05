package com.kto.employejetnexa.domain.usecase.employe

import com.kto.employejetnexa.domain.Repository
import com.kto.employejetnexa.domain.models.EmployeModel
import javax.inject.Inject

class UpdateEmploye @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(employeModel: EmployeModel, id: String) =
        repository.updateEmploye(employeModel, id)

}