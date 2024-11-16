package com.charan.setupBox.data.repository

import androidx.lifecycle.LiveData
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.utils.ProcessState

interface SupabaseRepo {

    suspend fun insertData(setupBoxContent: SetupBoxContent) : LiveData<ProcessState>
    suspend fun updateData(setupBoxContent: SetupBoxContent) : LiveData<ProcessState>
    suspend fun deleteData(id : Int) : LiveData<ProcessState>
    suspend fun getData():LiveData<ProcessState>

}