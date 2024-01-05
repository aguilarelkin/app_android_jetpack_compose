package com.kto.employejetnexa.ui.employe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employejetnexa.domain.models.EmployeModel
import com.kto.employejetnexa.domain.usecase.employe.DeleteEmploye
import com.kto.employejetnexa.domain.usecase.employe.GetEmploye
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EmployeViewModel @Inject constructor(
    private val getEmploye: GetEmploye,
    private val deleteEmploye: DeleteEmploye,
) : ViewModel() {
    private var _stateList = MutableStateFlow<List<EmployeModel>>(emptyList())
    val stateList: StateFlow<List<EmployeModel>> = _stateList

    private var _stateDelete = MutableStateFlow<Boolean>(false)
    val stateDelete: StateFlow<Boolean> = _stateDelete
    fun initState() {
        _stateDelete.value = false
    }

    fun getEmployes() {
        viewModelScope.launch {
            val result: List<EmployeModel>? = withContext(Dispatchers.IO) {
                getEmploye()
            }
            if (result != null) {
                _stateList.value = result
            }
        }
    }

    fun deleteEmployeId(id: String) {
        viewModelScope.launch {
            val result: String = withContext(Dispatchers.IO) {
                deleteEmploye(id)
            }
            if (result.isNotEmpty()) {
                _stateDelete.value = true
            }

        }
    }

}