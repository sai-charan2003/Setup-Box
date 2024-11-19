package com.charan.setupBox.data.repository

import com.charan.setupBox.data.remote.responsedto.TVAuthentication
import com.charan.setupBox.utils.LoginState
import com.charan.setupBox.utils.ProcessState
import kotlinx.coroutines.flow.Flow


interface SupabaseRepo {

    suspend fun getDataFromSupabase()

    suspend fun addCodeToTVTable() : Flow<LoginState>

    suspend fun observeData(code : String): Flow<TVAuthentication?>

    suspend fun authenticationBySessionId(code : String)

    suspend fun sentOTPLogin(mailId : String) : Flow<LoginState>

    suspend fun verifyOTP(mainId : String,otp : String) : Flow<LoginState>

    suspend fun checkAuthenticationStatus() : Flow<ProcessState>



}