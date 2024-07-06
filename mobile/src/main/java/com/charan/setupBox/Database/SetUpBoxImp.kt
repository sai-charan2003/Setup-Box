package com.charan.setupBox.Database

import kotlinx.coroutines.flow.Flow

class SetUpBoxImp(private val setUpBoxContentRepo: SetUpBoxContentRepo) {
    val allData: Flow<List<SetupBoxContent>> = setUpBoxContentRepo.getAllData()

    suspend fun insert(setupBoxContent: SetupBoxContent){
            setUpBoxContentRepo.insert(setupBoxContent)
    }

    fun getDataById(id:Int):SetupBoxContent{
        return setUpBoxContentRepo.getDataById(id)
    }

    fun update(setupBoxContent: SetupBoxContent){
        setUpBoxContentRepo.Update(setupBoxContent)
    }

    fun delete(id: Int){
        setUpBoxContentRepo.deleteDataById(id)
    }

    fun selectDistinctAppPackage():List<String?>{
        return setUpBoxContentRepo.selectDistinctAppPackage()
    }
}