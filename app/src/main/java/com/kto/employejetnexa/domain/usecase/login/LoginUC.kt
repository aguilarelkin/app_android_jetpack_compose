package com.kto.employejetnexa.domain.usecase.login

import com.kto.employejetnexa.domain.RepositoryLogin
import com.kto.employejetnexa.domain.models.LoginModel
import javax.inject.Inject

class LoginUC @Inject constructor(private val repositoryLogin: RepositoryLogin) {

    suspend operator fun invoke(loginModel: LoginModel) = repositoryLogin.loginPost(loginModel)
}