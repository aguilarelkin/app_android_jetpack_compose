package com.kto.employejetnexa.domain.usecase.servicem

import com.kto.employejetnexa.domain.RepositoryServicem
import javax.inject.Inject

class GetServicemId @Inject constructor(private val repositoryServicem: RepositoryServicem) {

    suspend operator fun invoke(id: String) = repositoryServicem.getServicemId(id)

}