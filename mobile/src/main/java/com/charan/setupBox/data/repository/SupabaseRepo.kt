package com.charan.setupBox.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.utils.ProcessState
import kotlinx.coroutines.flow.Flow

interface SupabaseRepo {

    suspend fun insertData(setupBoxContent: SetupBoxContent) : LiveData<ProcessState>
    suspend fun updateData(setupBoxContent: SetupBoxContent) : LiveData<ProcessState>
    suspend fun deleteData(id : Int) : LiveData<ProcessState>
    suspend fun getData():LiveData<ProcessState>
    suspend fun loginWithGoogle(context : Context): Flow<ProcessState>
    suspend fun attachSessionId(code : String,context:Context)

}