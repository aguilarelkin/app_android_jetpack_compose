package com.kto.employejetnexa.domain

import com.kto.employejetnexa.domain.models.EmployeModel

interface Repository {
    suspend fun getEmployes(): List<EmployeModel>?
    suspend fun getEmployeId(id: String): EmployeModel?
    suspend fun createEmploye(employeModel: EmployeModel): EmployeModel?
    suspend fun updateEmploye(employeModel: EmployeModel, id: String): EmployeModel?
    suspend fun deleteEmploye(id: String): String
}