package com.kto.employejetnexa.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employejetnexa.domain.models.LoginModel
import com.kto.employejetnexa.domain.usecase.login.LoginUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUC: LoginUC) : ViewModel() {


    private val _success = MutableStateFlow<Boolean>(false)
    val success: StateFlow<Boolean> = _success

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    private val _enableLogin = MutableStateFlow<Boolean>(false)
    val enableLogin: StateFlow<Boolean> = _enableLogin

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    suspend fun onLoginSelected() {
        _isLoading.value = true
        delay(4000)
        _isLoading.value = false
    }

    fun login(loginModel: LoginModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                loginUC(loginModel)
            }
            if (result != null) {
                _success.value = true
            }
        }
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _enableLogin.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isValidPassword(password: String): Boolean = password.length > 4

}