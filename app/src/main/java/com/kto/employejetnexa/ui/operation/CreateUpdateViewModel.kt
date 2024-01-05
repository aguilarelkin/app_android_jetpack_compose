package com.kto.employejetnexa.ui.operation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employejetnexa.domain.models.EmployeModel
import com.kto.employejetnexa.domain.models.RoleModel
import com.kto.employejetnexa.domain.usecase.employe.CreateEmploye
import com.kto.employejetnexa.domain.usecase.employe.GetEmployeId
import com.kto.employejetnexa.domain.usecase.employe.UpdateEmploye
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateUpdateViewModel @Inject constructor(
    private val createEmploye: CreateEmploye,
    private val getEmployeId: GetEmployeId,
    private val updateEmploye: UpdateEmploye,
) : ViewModel() {
    private var _stateEmploye = MutableStateFlow<EmployeModel>(EmployeModel())
    val stateEmploye: StateFlow<EmployeModel> = _stateEmploye

    private var _stateUC = MutableStateFlow(false)
    val stateUC: StateFlow<Boolean> = _stateUC

    private var _stateId = MutableStateFlow<String>("")
    val stateId: StateFlow<String> = _stateId
    private var _stateFirstname = MutableStateFlow<String>("")
    val stateFirstname: StateFlow<String> = _stateFirstname
    private var _stateLastname = MutableStateFlow<String>("")
    val stateLastname: StateFlow<String> = _stateLastname
    private var _statePassword = MutableStateFlow<String>("")
    val statePassword: StateFlow<String> = _statePassword
    private var _stateMail = MutableStateFlow<String>("")
    val stateMail: StateFlow<String> = _stateMail
    private var _stateAvatar = MutableStateFlow<String>("")
    val stateAvatar: StateFlow<String> = _stateAvatar
    private var _stateBase = MutableStateFlow<String>("")
    val stateBase: StateFlow<String> = _stateBase
    private var _stateRole = MutableStateFlow<List<RoleModel>>(emptyList())
    val stateRole: StateFlow<List<RoleModel>> = _stateRole
    private var _stateTelephone = MutableStateFlow<String>("")
    val stateTelephone: StateFlow<String> = _stateTelephone

    fun initState() {
        _stateUC.value = false
    }

    fun onChangedField(
        id: String,
        firstname: String,
        lastname: String,
        password: String,
        mail: String,
        avatar: String,
        base: String,
        role: List<RoleModel>,
        telephone: String,
    ) {
        _stateId.value = id
        _stateFirstname.value = firstname
        _stateLastname.value = lastname
        _statePassword.value = password
        _stateMail.value = mail
        _stateAvatar.value = avatar
        _stateBase.value = base
        _stateRole.value = emptyList()///
        _stateTelephone.value = telephone
    }

    fun createEmployes(employeModel: EmployeModel) {
        viewModelScope.launch {
            val result: EmployeModel? = withContext(Dispatchers.IO) {
                createEmploye(employeModel)
            }
            if (result != null) {
                _stateEmploye.value = result
                _stateUC.value = true
            }
        }
    }

    fun searchEmployeId(id: String) {
        viewModelScope.launch {
            val result: EmployeModel? = withContext(Dispatchers.IO) {
                getEmployeId(id)
            }
            if (result != null) {
                _stateEmploye.value = result
            }
        }
    }

    fun updateEmployesId(employeModel: EmployeModel, id: String) {
        viewModelScope.launch {
            val result: EmployeModel? = withContext(Dispatchers.IO) {
                updateEmploye(employeModel, id)
            }
            if (result != null) {
                _stateEmploye.value = result
                _stateUC.value = true
            }
        }
    }

}