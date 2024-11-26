package com.charan.setupBox.data.repository.impl

import com.charan.setupBox.data.local.dao.SetUpBoxContentDAO
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.data.repository.SetUpBoxRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetUpBoxRepoImp @Inject constructor(private val setUpBoxContentDAO: SetUpBoxContentDAO) : SetUpBoxRepo {
    override fun insert(setupBoxContent: SetupBoxContent) {
        setUpBoxContentDAO.insert(setupBoxContent)
    }

    override fun getAllData(): Flow<List<SetupBoxContent>> {
        return setUpBoxContentDAO.getAllData()
    }

    override fun getAllDataNonLiveData(): List<SetupBoxContent> {
        return setUpBoxContentDAO.getAllDataNonLiveData()
    }

    override fun deleteByUUID(id: String) {
        setUpBoxContentDAO.deleteById(id)
    }

    override fun update(setupBoxContent: SetupBoxContent) {
        setUpBoxContentDAO.update(setupBoxContent)
    }

    override fun getDataById(id: Int): SetupBoxContent {
        return setUpBoxContentDAO.getDataById(id)
    }

    override fun selectDistinctAppPackage(): List<String?> {
        return setUpBoxContentDAO.selectDistinctAppPackage()
    }

    override fun clearData() {
        setUpBoxContentDAO.clearData()
    }

    override fun getDataByUUID(uuid : String) : SetupBoxContent{
        return setUpBoxContentDAO.getDataByUUID(uuid)
    }



}