package com.kto.employejetnexa.domain.models

data class ServicemModel(
    val id: Int?,
    val service: String?,
    val price: Long?,
    val base: String?,
) {
    constructor() : this(null, null, null, null)
}
