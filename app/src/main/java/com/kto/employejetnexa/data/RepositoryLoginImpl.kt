package com.kto.employejetnexa.data

import android.util.Log
import com.kto.employejetnexa.data.core.datastore.DataInfo
import com.kto.employejetnexa.data.network.LoginApiService
import com.kto.employejetnexa.domain.RepositoryLogin
import com.kto.employejetnexa.domain.models.LoginModel
import javax.inject.Inject

class RepositoryLoginImpl @Inject constructor(
    private val loginApiService: LoginApiService,
    private val dataInfo: DataInfo,
) : RepositoryLogin {
    override suspend fun loginPost(loginModel: LoginModel): String? {
        runCatching { loginApiService.loginService(loginModel) }
            .onSuccess {
                dataInfo.saveToken(it.token)
                return it.token
            }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }
}