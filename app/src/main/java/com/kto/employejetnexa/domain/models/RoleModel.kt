package com.kto.employejetnexa.domain.models

data class RoleModel(
    val id: Int?,
    val name: String?,
) {
    constructor() : this(null, null)
}