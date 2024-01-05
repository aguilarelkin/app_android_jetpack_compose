package com.kto.employejetnexa.ui.employe

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kto.employejetnexa.domain.models.EmployeModel
import com.kto.employejetnexa.ui.delete.CustomDialog
import com.kto.employejetnexa.ui.navigation.bottommenu.NavRoutes

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Employe(navController: NavController, employeViewModel: EmployeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
/*        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Employe",
            tint = Color(0xFF0026FF),
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
        )*/
        InitData(Modifier.align(Alignment.BottomEnd), navController, employeViewModel)
    }
}

@Composable
fun InitData(modifier: Modifier, navController: NavController, employeViewModel: EmployeViewModel) {
    ListEmploye(employeViewModel, navController)
    ButtonCreate(modifier, navController)

}

@Composable
fun ListEmploye(employeViewModel: EmployeViewModel, navController: NavController) {
    val stateDelete by employeViewModel.stateDelete.collectAsState()

    if (stateDelete) {
        employeViewModel.getEmployes()
        employeViewModel.initState()
    }
    employeViewModel.getEmployes()
    val stateList: List<EmployeModel> by employeViewModel.stateList.collectAsState()

    if (stateList.isNotEmpty()) {
        LazyColumn {
            items(stateList) { model ->
                ListItemEm(item = model, navController, employeViewModel)
            }
        }
    }

}

private fun startOperation(navController: NavController, id: String) {
    navController.navigate(NavRoutes.CreateUpdate.route + "/${id}")
}

private fun deleteEmploye(employeViewModel: EmployeViewModel, id: String) {
    employeViewModel.deleteEmployeId(id)
}

@Composable
fun ListItemEm(
    item: EmployeModel,
    navController: NavController,
    employeViewModel: EmployeViewModel,
) {
    val showDialog: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    if (showDialog.value) {
        CustomDialog(
            deleteEm = { deleteEmploye(employeViewModel, item.id.toString()) },
            user = item.firstname.toString(),
            setShowDialog = {
                showDialog.value = it
            })
    }
    // val context = LocalContext.current
    /*  val onListClick = { text: String ->
          Toast.makeText(
              context,
              text,
              Toast.LENGTH_SHORT
          ).show()
      }*/
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(), shape = RoundedCornerShape(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.firstname.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )

            Button(onClick = { startOperation(navController, item.id.toString()) }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                showDialog.value = true
            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun ButtonCreate(modifier: Modifier, navController: NavController) {
    Box {
        FloatingActionButton(onClick = {
            navController.navigate(NavRoutes.LoginSystem.route)
        }, modifier = modifier.align(Alignment.TopEnd)) {
            Text(text = "Login")
        }
    }
    FloatingActionButton(onClick = {
        navController.navigate(NavRoutes.CreateUpdate.route + "/${null}")
    }, modifier = modifier) {
        Text(text = "+")
    }
}
