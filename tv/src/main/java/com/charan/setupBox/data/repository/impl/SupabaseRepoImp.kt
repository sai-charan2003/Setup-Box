package com.charan.setupBox.data.repository.impl

import android.util.Log
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.data.remote.supabaseClient
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.repository.SetUpBoxContentRepository
import com.charan.setupBox.utils.AppConstants
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class SupabaseRepoImp @Inject constructor(private val setUpBoxRepo: SetUpBoxContentRepository) : SupabaseRepo {
    override suspend fun getDataFromSupabase() {
        try {
            val remoteData = supabaseClient.client.from(AppConstants.SETUPBOXCONTENT).select()
                .decodeList<SetupBoxContent>()
            val localData = setUpBoxRepo.getAllDataNonLiveData()
            val localDataIds = localData.map { it.id }.toSet()
            val remoteDataIds = remoteData.map { it.id }.toSet()
            val itemsToInsert = remoteData.filter { it.id !in localDataIds }
            val itemsToRemove = localData.filter { it.id !in remoteDataIds }
            val itemsToUpdate = remoteData.filter { remoteItem ->
                localData.any { localItem ->
                    localItem.id == remoteItem.id && localItem != remoteItem
                }
            }
            itemsToInsert.forEach {
                setUpBoxRepo.insert(it)
            }
            itemsToRemove.forEach {
                setUpBoxRepo.deleteById(it.id!!)
            }
            itemsToUpdate.forEach { updatedItem ->
                setUpBoxRepo.update(updatedItem)
            }
        }  catch (e: Exception) {
            Log.e("SupabaseError", e.message.toString())
        }
    }
}