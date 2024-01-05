package com.kto.employejetnexa.domain

import com.kto.employejetnexa.domain.models.LoginModel

interface RepositoryLogin {
    suspend fun loginPost(loginModel: LoginModel): String?
}