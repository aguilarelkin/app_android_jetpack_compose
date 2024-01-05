package com.kto.employejetnexa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kto.employejetnexa.domain.models.RoleModel

data class RoleResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) {
    fun toDomain() = RoleModel(
        id, name
    )
}