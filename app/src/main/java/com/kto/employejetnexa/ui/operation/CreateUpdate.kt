@file:OptIn(ExperimentalMaterial3Api::class)

package com.kto.employejetnexa.ui.operation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kto.employejetnexa.domain.models.EmployeModel
import com.kto.employejetnexa.domain.models.RoleModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdate(
    navHostController: NavController, id: String?,
    createUpdateViewModel: CreateUpdateViewModel,
) {
    Scaffold(content = {
        Box {
            Main(navHostController, id, Modifier.align(Alignment.TopStart), createUpdateViewModel)
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    navHostController: NavController,
    id: String?,
    modifier: Modifier,
    createUpdateViewModel: CreateUpdateViewModel,
) {
    val scrollState = rememberScrollState()

    val stateEmploye: EmployeModel by createUpdateViewModel.stateEmploye.collectAsState()
    val stateUC: Boolean by createUpdateViewModel.stateUC.collectAsState()

    val ids: String by createUpdateViewModel.stateId.collectAsState()
    val firstname: String by createUpdateViewModel.stateFirstname.collectAsState()
    val lastname: String by createUpdateViewModel.stateLastname.collectAsState()
    val password: String by createUpdateViewModel.statePassword.collectAsState()
    val mail: String by createUpdateViewModel.stateMail.collectAsState()
    val avatar: String by createUpdateViewModel.stateAvatar.collectAsState()
    val base: String by createUpdateViewModel.stateBase.collectAsState()
    val role: List<RoleModel> by createUpdateViewModel.stateRole.collectAsState()
    val telephone: String by createUpdateViewModel.stateTelephone.collectAsState()

    val okRestApi: MutableState<Boolean> = rememberSaveable {
        mutableStateOf(false)
    }

    if (stateUC) {
        createUpdateViewModel.initState()
        if (id != "null") {
            navHostController.popBackStack()
        } else {
            createUpdateViewModel.onChangedField("", "", "", "", "", "", "", emptyList(), "")
        }
    }

    if (id != "null" && !okRestApi.value) {
        createUpdateViewModel.searchEmployeId(id!!)
        if (stateEmploye.mail != null) {
            okRestApi.value = true
            createUpdateViewModel.onChangedField(
                stateEmploye.id.toString(),
                stateEmploye.firstname ?: "",
                stateEmploye.lastname ?: "",
                stateEmploye.password ?: "",
                stateEmploye.mail ?: "",
                stateEmploye.avatar ?: "",
                stateEmploye.base ?: "",
                stateEmploye.role ?: emptyList(),
                stateEmploye.telephone ?: ""
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            modifier = modifier
                .padding(20.dp)
                .clickable { navHostController.popBackStack() }
        )
        Text(text = id ?: "null")
        EditeId(ids) {
            createUpdateViewModel.onChangedField(
                it,
                firstname,
                lastname,
                password,
                mail,
                avatar,
                base,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeFirstname(firstname) {
            createUpdateViewModel.onChangedField(
                ids,
                it,
                lastname,
                password,
                mail,
                avatar,
                base,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeLastname(lastname) {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                it,
                password,
                mail,
                avatar,
                base,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeEmail(mail) {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                lastname,
                password,
                it,
                avatar,
                base,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeAvatar(avatar) {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                lastname,
                password,
                mail,
                it,
                base,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeBase(base) {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                lastname,
                password,
                mail,
                avatar,
                it,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeTelephone(telephone) {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                lastname,
                password,
                mail,
                avatar,
                base,
                role,
                it
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditePassword(password) {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                lastname,
                it,
                mail,
                avatar,
                base,
                role,
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditeRole("ROLE_USER") {
            createUpdateViewModel.onChangedField(
                ids,
                firstname,
                lastname,
                password,
                mail,
                avatar,
                base,
                emptyList(),
                telephone
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        ButtonOpe(id) {
            operation(
                id, EmployeModel(
                    validId(ids),
                    firstname,
                    lastname,
                    password,
                    mail,
                    avatar,
                    base,
                    listOf(RoleModel(3, "ROLE_USER")),
                    telephone
                ), createUpdateViewModel
            )
        }
    }

}

private fun operation(
    id: String?,
    employeModel: EmployeModel,
    createUpdateViewModel: CreateUpdateViewModel,
) {
    if (id == "null") {
        createUpdateViewModel.createEmployes(
            employeModel
        )
    } else {
        createUpdateViewModel.updateEmployesId(
            employeModel.copy(password = "12334"), id!!
        )
    }
}

@Composable
fun EditeId(ids: String, changedField: (String) -> Unit) {
    Text(text = "ID")
    TextField(
        value = ids, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "89") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun EditeFirstname(firstname: String, changedField: (String) -> Unit) {
    Text(text = "Firstname")
    TextField(
        value = firstname, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "Natalia") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditeLastname(lastname: String, changedField: (String) -> Unit) {
    Text(text = "Lastname")
    TextField(
        value = lastname, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "Paz") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditeEmail(mail: String, changedField: (String) -> Unit) {
    Text(text = "Email")
    TextField(
        value = mail, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "natalia@gmail.com") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun EditeAvatar(avatar: String, changedField: (String) -> Unit) {
    Text(text = "Avatar")
    TextField(
        value = avatar, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "natalia.png") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditeBase(base: String, changedField: (String) -> Unit) {
    Text(text = "Base")
    TextField(
        value = base, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "Admin") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditeTelephone(telephone: String, changedField: (String) -> Unit) {
    Text(text = "Telephone")
    TextField(
        value = telephone, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "3561217801") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

@Composable
fun EditePassword(password: String, changedField: (String) -> Unit) {
    Text(text = "Password")
    TextField(
        value = password, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "*************") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun EditeRole(role: String, changedField: (String) -> Unit) {
    Text(text = "Lastname")
    TextField(
        value = role, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "ROLE_ADMIN") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun ButtonOpe(id: String?, changedField: () -> Unit) {
    Button(
        onClick = { changedField() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00C853),
            disabledContainerColor = Color(0xFF00B8D4),
            contentColor = Color(0xFFFFFFFF),
            disabledContentColor = Color(0xFFFFFFFF)
        )//, enabled = loginEnable
    ) {
        if (id == "null") {
            Text(text = "Create")
        } else {
            Text(text = "Update")
        }
    }
}


private fun validId(edId: String): Int {
    return try {
        Integer.parseInt(edId)
    } catch (e: Exception) {
        0
    }
}
