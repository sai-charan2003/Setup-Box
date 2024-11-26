package com.charan.setupBox.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(private val supabaseRepo: SupabaseRepo) : ViewModel() {
    val _logoutStatus = MutableStateFlow<ProcessState?>(null)
    val logoutStatus = _logoutStatus.asStateFlow()
    val _bottomSheet = MutableStateFlow<Boolean>(false)
    val bottomSheetStatus = _bottomSheet.asStateFlow()
    private val _textFieldValue = MutableStateFlow("")
    val textFieldValue = _textFieldValue.asStateFlow()
    private val _authenticationStatus = MutableStateFlow<ProcessState?>(null)
    val authenticationStatus = _authenticationStatus.asStateFlow()


    fun logout(){
        _logoutStatus.tryEmit(ProcessState.Loading)
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.logout().collectLatest {
                _logoutStatus.tryEmit(it)
            }
        }
    }

    fun showModalBottomSheet(){
        _bottomSheet.value = true

    }
    fun hideBottomSheet(){
        _bottomSheet.value = false
        resetAuthenticationCode()
    }

    fun authenticateCode(text : String){
        _textFieldValue.value = text
    }

    fun resetAuthenticationCode(){
        _textFieldValue.value = ""
    }

    fun addAuthenticationToken(context: Context){
        _authenticationStatus.tryEmit(ProcessState.Loading)
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.attachEmailIdToCode(_textFieldValue.value,context).collectLatest {
                _authenticationStatus.tryEmit(it)
            }
        }

    }

}