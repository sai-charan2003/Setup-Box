package com.charan.setupBox.repository

import com.charan.setupBox.data.local.entity.SetupBoxContent
import kotlinx.coroutines.flow.Flow

interface SetUpBoxContentRepository {
    fun insert(setupBoxContent: SetupBoxContent)
    fun getAllData(): Flow<List<SetupBoxContent>>
    fun getAllDataNonLiveData(): List<SetupBoxContent>
    fun deleteById(id: Int)
    fun update(setupBoxContent: SetupBoxContent)
}
