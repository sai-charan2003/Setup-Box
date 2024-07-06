package com.charan.setupBox.Database

import kotlinx.coroutines.flow.Flow

class SetUpBoxImp(private val setUpBoxContentRepo: SetUpBoxContentRepo) {
    val allData: Flow<List<SetupBoxContent>> = setUpBoxContentRepo.getAllData()

    suspend fun insert(setupBoxContent: SetupBoxContent){
            setUpBoxContentRepo.insert(setupBoxContent)
    }

    fun getAllData(): List<SetupBoxContent>{
        return setUpBoxContentRepo.getAllDataNonLiveData()
    }

    fun deleteById(id:Int){
        setUpBoxContentRepo.deleteById(id)
    }
}