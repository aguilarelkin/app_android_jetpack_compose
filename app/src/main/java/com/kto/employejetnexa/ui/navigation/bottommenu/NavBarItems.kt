package com.kto.employejetnexa.ui.navigation.bottommenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart

object NavBarItems {

    val barItems = listOf(
        BarItem(
            title = "Empleados",
            image = Icons.Filled.Person,
            route = "employe"
        ),
        BarItem(
            title = "Services",
            image = Icons.Filled.ShoppingCart,
            route = "service"
        )

    )

}