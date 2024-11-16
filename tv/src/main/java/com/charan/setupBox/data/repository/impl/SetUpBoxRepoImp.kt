package com.charan.setupBox.data.repository.impl

import com.charan.setupBox.data.local.dao.SetUpBoxContentDAO
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.repository.SetUpBoxContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetUpBoxRepoImp @Inject constructor(private val setUpBoxContentDAO: SetUpBoxContentDAO) : SetUpBoxContentRepository {

    override fun insert(setupBoxContent: SetupBoxContent) {
        setUpBoxContentDAO.insert(setupBoxContent)
    }

    override fun getAllData(): Flow<List<SetupBoxContent>> {
        return setUpBoxContentDAO.getAllData()
    }

    override fun getAllDataNonLiveData(): List<SetupBoxContent> {
        return setUpBoxContentDAO.getAllDataNonLiveData()
    }

    override fun deleteById(id: Int) {
        setUpBoxContentDAO.deleteById(id)
    }

    override fun update(setupBoxContent: SetupBoxContent) {
        setUpBoxContentDAO.update(setupBoxContent)
    }


}