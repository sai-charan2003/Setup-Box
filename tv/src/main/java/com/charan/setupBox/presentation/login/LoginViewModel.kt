package com.charan.setupBox.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.utils.LoginState
import com.charan.setupBox.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.Identity.decode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val supabaseRepo: SupabaseRepo) : ViewModel() {


    private val _authenticationStatus  = MutableStateFlow<ProcessState?>(null)
    val authenticationStatus = _authenticationStatus.asStateFlow()

    private val _optTextField = MutableStateFlow("")
    val otpTextField = _optTextField.asStateFlow()





    private val _loginState  = MutableStateFlow<LoginState?>(null)
    val loginState = _loginState.asStateFlow()


    suspend fun getAuthenticationCode()  {
        _loginState.tryEmit(LoginState.Loading)
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.addCodeToTVTable().collectLatest {
                _loginState.tryEmit(it)

            }
        }

    }

    fun observerOTPStatus(code : String){

        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.observeData(code).collectLatest {
                Log.d("TAG", "observerOTPStatus: $it")
                if (it != null) {
                    if(it.email.isNullOrEmpty().not()){
                        supabaseRepo.sentOTPLogin(it.email!!).collectLatest {
                            _loginState.tryEmit(it)

                        }
                    }
                }

            }

        }

    }

    fun verifyOTPStatus(email : String){
        _loginState.tryEmit(LoginState.Loading)
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.verifyOTP(email,_optTextField.value).collectLatest {
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

    fun otpTextValue(code : String){
        _optTextField.value = code
    }
    fun resetTextField(){
        _optTextField.value = ""
    }

    fun changeAuthenticationStatus(code : String){
        viewModelScope.launch {
            supabaseRepo.updateAuthenticationStatus(code)
        }
    }



}