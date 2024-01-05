package com.kto.employejetnexa.ui.navigation.bottommenu

//415
sealed class NavRoutes(val route: String) {
    object Employe : NavRoutes("employe")
    object Servicem : NavRoutes("service")
    object CreateUpdate : NavRoutes("create")
    object LoginSystem:NavRoutes("login")
    object CreateUpdateSe:NavRoutes("createservice")

}
