package com.kto.employejetnexa.domain.usecase.servicem

import com.kto.employejetnexa.domain.RepositoryServicem
import com.kto.employejetnexa.domain.models.ServicemModel
import javax.inject.Inject

class UpdateServicem @Inject constructor(private val repositoryServicem: RepositoryServicem) {
    suspend operator fun invoke(servicemModel: ServicemModel, id: String) =
        repositoryServicem.updateServicem(servicemModel, id)
}