package com.charan.setupBox.presentation.login

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
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val supabaseRepo: SupabaseRepo) : ViewModel() {

    private val _googleAuthProcess = MutableStateFlow<ProcessState?>(null)
    val googleAuthProcess = _googleAuthProcess.asStateFlow()



    fun loginWithGoogle(context: Context)  {
        _googleAuthProcess.tryEmit(ProcessState.Loading)
        viewModelScope.launch {
            supabaseRepo.loginWithGoogle(context).collectLatest {
                _googleAuthProcess.tryEmit(it)
            }

        }


    }



}