@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.kto.employejetnexa.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kto.employejetnexa.R
import com.kto.employejetnexa.domain.models.LoginModel

@Composable
fun LoginActivity(viewModel: LoginViewModel) {

    val email: String by viewModel.email.collectAsState()
    val password: String by viewModel.password.collectAsState()
    val loginEnable: Boolean by viewModel.enableLogin.collectAsState()
    val isLoading: Boolean by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel, email, password, loginEnable, isLoading)
    }
}

@Composable
fun Login(
    modifier: Modifier,
    viewModel: LoginViewModel,
    email: String,
    password: String,
    loginEnable: Boolean,
    isLoading: Boolean,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val success: Boolean by viewModel.success.collectAsState()

    val corrutineScope = rememberCoroutineScope()

    if (success) {
        Toast.makeText(context, "token", Toast.LENGTH_LONG).show()
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier.verticalScroll(scrollState)) {
            ImageLogin(
                Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            TextView(Modifier.align(Alignment.CenterHorizontally), "EMAIL")
            Spacer(modifier = Modifier.padding(8.dp))
            EmailEditText(email) { viewModel.onLoginChanged(it, password) }

            Spacer(modifier = Modifier.padding(8.dp))
            TextView(Modifier.align(Alignment.CenterHorizontally), "PASSWORD")
            Spacer(modifier = Modifier.padding(8.dp))
            PasswordText(password) { viewModel.onLoginChanged(email, it) }

            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))

            Spacer(modifier = Modifier.padding(16.dp))
            ButtonLogin(loginEnable) {
                viewModel.login(LoginModel(email, password))
            }
        }
    }
}

@Composable
fun ButtonLogin(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00C853),
            disabledContainerColor = Color(0xFF00B8D4),
            contentColor = Color(0xFFFFFFFF),
            disabledContentColor = Color(0xFFFFFFFF)
        ), enabled = loginEnable
    ) {
        Text(text = "Login")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "¿Forgot password?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF304FFE)
    )
}

@Composable
fun TextView(modifier: Modifier, name: String) {
    Text(
        text = name,
        modifier = modifier,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF001BB4)
    )
}

@Composable
fun PasswordText(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "********") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Blue,
            containerColor = Color(0xFF000000),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun EmailEditText(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "Email") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Blue,
            containerColor = Color(0xFF000000),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun ImageLogin(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.perfil),
        modifier = modifier.size(200.dp),
        contentDescription = "Login"
    )
}

