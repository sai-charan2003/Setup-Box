package com.charan.setupBox.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.utils.LoginState
import com.charan.setupBox.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val supabaseRepo: SupabaseRepo) : ViewModel() {


    private val _authenticationStatus  = MutableStateFlow<ProcessState?>(null)
    val authenticationStatus = _authenticationStatus.asStateFlow()



    private val _loginState  = MutableStateFlow<LoginState?>(null)
    val loginState = _loginState.asStateFlow()


    suspend fun getAuthenticationCode()  {
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.addCodeToTVTable().collectLatest {
                _loginState.tryEmit(it)
            }
        }

    }

    fun observerOTPStatus(code : String){
        Log.d("TAG", "observerOTPStatus: hi")
        val job = viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.observeData(code).collectLatest {
                Log.d("TAG", "observerOTPStatus: $it")
                if (it != null) {
                    if(it.session_id.isNullOrEmpty().not()){

                        supabaseRepo.sentOTPLogin(it.session_id!!).collectLatest {
                            _loginState.tryEmit(it)

                        }
                    }
                }

            }

        }

    }

    fun verifyOTPStatus(email : String,code : String){
        Log.d("TAG", "verifyOTPStatus: $email")
        Log.d("TAG", "verifyOTPStatus: $code")

        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.verifyOTP(email,code).collectLatest {
                _loginState.tryEmit(it)
            }
        }

    }

    fun authenticationStatus() {
        viewModelScope.launch {
            supabaseRepo.checkAuthenticationStatus().collectLatest {
                _authenticationStatus.tryEmit(it)
            }
        }
    }
    fun resetLoginState() {
        _loginState.tryEmit(null)
    }



}