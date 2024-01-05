@file:OptIn(ExperimentalMaterial3Api::class)

package com.kto.employejetnexa.ui.operationser

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
import com.kto.employejetnexa.domain.models.ServicemModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateServicem(
    navHostController: NavController, id: String?,
    creUpServiceViewModel: CreUpServiceViewModel,
) {
    Scaffold(content = {
        Box {
            MainService(
                navHostController,
                id,
                Modifier.align(Alignment.TopStart),
                creUpServiceViewModel
            )
        }
    })
}

@Composable
fun MainService(
    navHostController: NavController,
    id: String?,
    modifier: Modifier,
    creUpServiceViewModel: CreUpServiceViewModel,
) {
    val scrollState = rememberScrollState()
    val stateService by creUpServiceViewModel.stateService.collectAsState()
    val stateUCs by creUpServiceViewModel.stateUCs.collectAsState()

    val stateId by creUpServiceViewModel.stateId.collectAsState()
    val stateName by creUpServiceViewModel.stateName.collectAsState()
    val statePrice by creUpServiceViewModel.statePrice.collectAsState()
    val stateBase by creUpServiceViewModel.stateBase.collectAsState()

    val okRestApi: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    if (stateUCs) {
        creUpServiceViewModel.initState()
        if (id != "null") {
            navHostController.popBackStack()
        } else {
            creUpServiceViewModel.onChangedField("", "", "", "")
        }
    }

    if (id != "null" && !okRestApi.value) {
        creUpServiceViewModel.searchServicemIds(id!!)
        if (stateService.service != null) {
            okRestApi.value = true
            creUpServiceViewModel.onChangedField(
                stateService.id.toString(),
                stateService.service ?: "",
                stateService.price.toString(),
                stateService.base ?: ""
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            modifier = modifier
                .padding(20.dp)
                .clickable { navHostController.popBackStack() }
        )
        Text(text = id ?: "null")
        EditId(stateId) {
            creUpServiceViewModel.onChangedField(
                it,
                stateName,
                statePrice,
                stateBase
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditService(stateName) {
            creUpServiceViewModel.onChangedField(
                stateId,
                it,
                statePrice,
                stateBase
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditPrice(statePrice) {
            creUpServiceViewModel.onChangedField(
                stateId,
                stateName,
                it,
                stateBase
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        EditBase(stateBase) {
            creUpServiceViewModel.onChangedField(
                stateId,
                stateName,
                statePrice,
                it
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        ButtonOpeSe(id) {
            operationSe(
                id, ServicemModel(
                    validId(stateId),
                    stateName,
                    validPrice(statePrice),
                    stateBase
                ), creUpServiceViewModel
            )
        }

    }
}

@Composable
fun ButtonOpeSe(id: String?, changedField: () -> Unit) {
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

@Composable
fun EditBase(stateBase: String, changedField: (String) -> Unit) {
    Text(text = "Base")
    TextField(
        value = stateBase, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "Admin") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditPrice(statePrice: String, changedField: (String) -> Unit) {
    Text(text = "Price")
    TextField(
        value = statePrice, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "450000") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun EditService(stateService: String, changedField: (String) -> Unit) {
    Text(text = "Service")
    TextField(
        value = stateService, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "Administración") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditId(stateId: String, changedField: (String) -> Unit) {
    Text(text = "ID")
    TextField(
        value = stateId, onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = "80") },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

fun operationSe(
    id: String?,
    servicemModel: ServicemModel,
    creUpServiceViewModel: CreUpServiceViewModel,
) {
    if (id == "null") {
        creUpServiceViewModel.createServicems(
            servicemModel
        )
    } else {
        creUpServiceViewModel.updateServicemIds(
            servicemModel, id!!
        )
    }
}

private fun validId(edId: String): Int {
    return try {
        Integer.parseInt(edId)
    } catch (e: Exception) {
        0
    }
}

private fun validPrice(edPrice: String): Long {
    return try {
        edPrice.toLong()
    } catch (e: Exception) {
        0
    }
}

