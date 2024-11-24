package com.charan.setupBox.data.repository

import android.content.Context
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.utils.ProcessState
import kotlinx.coroutines.flow.Flow

interface SupabaseRepo {

    suspend fun insertData(setupBoxContent: SetupBoxContent) : Flow<ProcessState>
    suspend fun updateData(setupBoxContent: SetupBoxContent) : Flow<ProcessState>
    suspend fun deleteData(id : String) : Flow<ProcessState>
    suspend fun getData():Flow<ProcessState>
    suspend fun loginWithGoogle(context : Context): Flow<ProcessState>
    suspend fun attachEmailIdToCode(code : String, context:Context) : Flow<ProcessState>
    suspend fun logout() : Flow<ProcessState>

}