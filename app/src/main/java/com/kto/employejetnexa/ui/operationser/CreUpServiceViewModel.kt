package com.kto.employejetnexa.ui.operationser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employejetnexa.domain.models.ServicemModel
import com.kto.employejetnexa.domain.usecase.servicem.CreateServicem
import com.kto.employejetnexa.domain.usecase.servicem.GetServicemId
import com.kto.employejetnexa.domain.usecase.servicem.UpdateServicem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreUpServiceViewModel @Inject constructor(
    private val createServicem: CreateServicem,
    private val getServicemId: GetServicemId,
    private val updateServicem: UpdateServicem,
) : ViewModel() {
    private var _stateService = MutableStateFlow<ServicemModel>(ServicemModel())
    val stateService: StateFlow<ServicemModel> = _stateService

    private var _stateUCs = MutableStateFlow<Boolean>(false)
    val stateUCs: StateFlow<Boolean> = _stateUCs

    private var _stateId = MutableStateFlow<String>("")
    val stateId: StateFlow<String> = _stateId
    private var _stateName = MutableStateFlow<String>("")
    val stateName: StateFlow<String> = _stateName
    private var _statePrice = MutableStateFlow<String>("")
    val statePrice: StateFlow<String> = _statePrice
    private var _stateBase = MutableStateFlow<String>("")
    val stateBase: StateFlow<String> = _stateBase

    fun initState() {
        _stateUCs.value = false
    }

    fun onChangedField(id: String, name: String, price: String, base: String) {
        _stateId.value = id
        _stateName.value = name
        _statePrice.value = price
        _stateBase.value = base
    }


    fun createServicems(servicemModel: ServicemModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                createServicem(servicemModel)
            }
            if (result != null) {
                _stateService.value = result
                _stateUCs.value = true
            }
        }
    }

    fun searchServicemIds(id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getServicemId(id)
            }
            if (result != null) {
                _stateService.value = result
            }
        }
    }

    fun updateServicemIds(servicemModel: ServicemModel, id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                updateServicem(servicemModel, id)
            }
            if (result != null) {
                _stateService.value = result
                _stateUCs.value = true
            }
        }
    }
}