package com.charan.setupBox.data.repository

import com.charan.setupBox.data.local.entity.SetupBoxContent
import kotlinx.coroutines.flow.Flow

interface SetUpBoxRepo {

    fun insert(setupBoxContent: SetupBoxContent)
    fun getAllData(): Flow<List<SetupBoxContent>>
    fun getAllDataNonLiveData(): List<SetupBoxContent>
    fun deleteById(id: Int)
    fun update(setupBoxContent: SetupBoxContent)
    fun getDataById(id:Int): SetupBoxContent
    fun selectDistinctAppPackage() : List<String?>
}