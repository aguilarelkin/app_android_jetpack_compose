package com.kto.employejetnexa.motherobject

import com.kto.employejetnexa.data.network.response.EmployeResponse
import com.kto.employejetnexa.motherobject.RoleMotherObject.anyRoleResponse

object EmployeMotherObject {
    val anyResponse = EmployeResponse(
        1,
        "Natalia",
        "Paz",
        "natalia@gmail.com",
        "12345",
        "natalia.jpg",
        "develoment",
        anyRoleResponse,
        "3124349059"
    )
}