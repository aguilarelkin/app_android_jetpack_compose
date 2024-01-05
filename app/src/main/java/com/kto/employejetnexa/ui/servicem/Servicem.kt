package com.kto.employejetnexa.ui.servicem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kto.employejetnexa.domain.models.ServicemModel
import com.kto.employejetnexa.ui.delete.CustomDialog
import com.kto.employejetnexa.ui.navigation.bottommenu.NavRoutes

@Composable
fun Servicem(navController: NavController, servicemViewModel: ServicemViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {
        InitData(Modifier.align(Alignment.BottomEnd), navController, servicemViewModel)
    }
}

@Composable
fun InitData(
    modifier: Modifier,
    navController: NavController,
    servicemViewModel: ServicemViewModel,
) {
    ListServicem(servicemViewModel, navController)
    ButtonCreate(modifier, navController)
}

@Composable
fun ListServicem(servicemViewModel: ServicemViewModel, navController: NavController) {
    val stateDelete by servicemViewModel.stateDelete.collectAsState()
    if (stateDelete) {
        servicemViewModel.getServicemList()
        servicemViewModel.initDelete()
    }
    servicemViewModel.getServicemList()
    val stateService by servicemViewModel.stateService.collectAsState()
    if (stateService.isNotEmpty()) {
        LazyColumn {
            items(stateService) { model ->
                ListItemSe(model, servicemViewModel, navController)
            }
        }
    }
}

@Composable
fun ListItemSe(
    item: ServicemModel,
    servicemViewModel: ServicemViewModel,
    navController: NavController,
) {
    val showDialog = rememberSaveable { mutableStateOf(false) }
    if (showDialog.value) {
        CustomDialog(
            deleteEm = { deleteService(servicemViewModel, item.id.toString()) },
            user = item.service!!,
            setShowDialog = { showDialog.value = it })
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(), shape = RoundedCornerShape(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.service!!,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            Button(onClick = { startOperation(navController, item.id.toString()) }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = { showDialog.value = true }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

private fun startOperation(navController: NavController, id: String) {
    navController.navigate(NavRoutes.CreateUpdateSe.route + "/${id}")
}

private fun deleteService(servicemViewModel: ServicemViewModel, id: String) {
    servicemViewModel.deleteServicemIds(id)
}

@Composable
fun ButtonCreate(modifier: Modifier, navController: NavController) {

    Box {
        FloatingActionButton(
            onClick = { navController.navigate(NavRoutes.CreateUpdateSe.route + "/${null}") },
            modifier = modifier
        ) {
            Text(text = "+")
        }
    }
}
