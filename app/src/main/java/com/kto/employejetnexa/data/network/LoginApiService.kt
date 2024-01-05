package com.kto.employejetnexa.data.network

import com.kto.employejetnexa.data.network.response.LoginResponse
import com.kto.employejetnexa.domain.models.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/login")
    suspend fun loginService(@Body loginModel: LoginModel): LoginResponse
}