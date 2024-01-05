package com.kto.employejetnexa.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kto.employejetnexa.ui.employe.Employe
import com.kto.employejetnexa.ui.employe.EmployeViewModel
import com.kto.employejetnexa.ui.login.LoginActivity
import com.kto.employejetnexa.ui.login.LoginViewModel
import com.kto.employejetnexa.ui.navigation.bottommenu.NavBarItems
import com.kto.employejetnexa.ui.navigation.bottommenu.NavRoutes
import com.kto.employejetnexa.ui.operation.CreateUpdate
import com.kto.employejetnexa.ui.operation.CreateUpdateViewModel
import com.kto.employejetnexa.ui.operationser.CreUpServiceViewModel
import com.kto.employejetnexa.ui.operationser.CreateUpdateServicem
import com.kto.employejetnexa.ui.servicem.Servicem
import com.kto.employejetnexa.ui.servicem.ServicemViewModel
import com.kto.employejetnexa.ui.theme.EmployejetnexaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //private val employeViewModel: EmployeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployejetnexaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // LoginActivity(LoginViewModel())
                    MainNav()
                }
            }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav() {
    val navController = rememberNavController()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "EMPLOYENEXA") })
    }, content = { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            NavigationHost(navController = navController)
        }
    }, bottomBar = { BottomNavigationBar(navController = navController) })
}

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Employe.route,
    ) {
        composable(NavRoutes.Employe.route) {
            val employeViewModel = hiltViewModel<EmployeViewModel>()
            Employe(navController = navController, employeViewModel)
        }
        composable(NavRoutes.Servicem.route) {
            val servicemViewModel = hiltViewModel<ServicemViewModel>()
            Servicem(navController, servicemViewModel)
        }
        composable(
            NavRoutes.CreateUpdate.route + "/{id}"
        ) {
            val createUpdateViewModel = hiltViewModel<CreateUpdateViewModel>()
            CreateUpdate(navController, it.arguments?.getString("id"), createUpdateViewModel)
        }
        composable(NavRoutes.CreateUpdateSe.route + "/{id}") {
            val creUpServiceViewModel = hiltViewModel<CreUpServiceViewModel>()
            CreateUpdateServicem(
                navHostController = navController,
                id = it.arguments?.getString("id"),
                creUpServiceViewModel = creUpServiceViewModel
            )
        }
        composable(NavRoutes.LoginSystem.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginActivity(viewModel = loginViewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        NavBarItems.barItems.forEach { navItem ->
            NavigationBarItem(selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = navItem.title) },
                icon = { Icon(imageVector = navItem.image, contentDescription = navItem.title) })
        }
    }
}

