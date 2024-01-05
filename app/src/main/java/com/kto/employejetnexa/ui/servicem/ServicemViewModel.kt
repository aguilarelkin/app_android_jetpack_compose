package com.kto.employejetnexa.ui.servicem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employejetnexa.domain.models.ServicemModel
import com.kto.employejetnexa.domain.usecase.servicem.DeleteServicem
import com.kto.employejetnexa.domain.usecase.servicem.GetServicem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServicemViewModel @Inject constructor(
    private val getServicem: GetServicem, private val deleteServicem: DeleteServicem,
) : ViewModel() {

    private var _stateService = MutableStateFlow<List<ServicemModel>>(emptyList())
    val stateService: StateFlow<List<ServicemModel>> = _stateService

    private var _stateDelete = MutableStateFlow<Boolean>(false)
    val stateDelete: StateFlow<Boolean> = _stateDelete

    fun initDelete(){
        _stateDelete.value = false
    }

    fun getServicemList() {
        viewModelScope.launch {
            val result: List<ServicemModel>? = withContext(Dispatchers.IO) {
                getServicem()
            }
            if (result != null) {
                _stateService.value = result
            }
        }
    }

    fun deleteServicemIds(id: String) {
        viewModelScope.launch {
            val result: String = withContext(Dispatchers.IO) {
                deleteServicem(id)
            }
            if (result.isNotEmpty()) {
                _stateDelete.value = true
            }
        }
    }
}