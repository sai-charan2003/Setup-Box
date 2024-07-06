package com.charan.setupBox.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.Database.AppDatabase
import com.charan.setupBox.Database.SetUpBoxContentRepo
import com.charan.setupBox.Database.SetUpBoxImp
import com.charan.setupBox.Database.SetupBoxContent
import com.charan.setupBox.SupaBase.supabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModel(application: Application): ViewModel() {
    private val setUpBoxContentRepo: SetUpBoxContentRepo
    private val setUpBoxImp: SetUpBoxImp
    private val _allData = MutableStateFlow(emptyList<SetupBoxContent>())
    val allData = _allData.asStateFlow()

    init {
        val setUpBoxContentDatabase= AppDatabase.getDatabase(application)
        setUpBoxContentRepo = setUpBoxContentDatabase.setupBoxRepo()
        setUpBoxImp = SetUpBoxImp(setUpBoxContentRepo)
        viewModelScope.launch {
            setUpBoxImp.allData.collectLatest {
                _allData.tryEmit(it)
            }
        }

    }

    private fun insert(setupBoxContent: SetupBoxContent)=viewModelScope.launch(Dispatchers.IO){
        setUpBoxContentRepo.insert(setupBoxContent)
    }

    fun getSupabaseData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val remoteData = supabaseClient.client.from("SetupBoxContent").select()
                    .decodeList<SetupBoxContent>()
                Log.d("TAG", "getSupabaseData: $remoteData")
                val localData = setUpBoxContentRepo.getAllDataNonLiveData()

                val localDataIds = localData.map { it.id }.toSet()
                val remoteDataIds = remoteData.map { it.id }.toSet()

                val itemsToInsert = remoteData.filter { it.id !in localDataIds }
                val itemsToRemove = localData.filter { it.id !in remoteDataIds }

                // Identify items to update
                val itemsToUpdate = remoteData.filter { remoteItem ->
                    localData.any { localItem ->
                        localItem.id == remoteItem.id && localItem != remoteItem
                    }
                }

                itemsToInsert.forEach {
                    insert(it)
                }

                itemsToRemove.forEach {
                    setUpBoxContentRepo.deleteById(it.id!!)
                }

                // Update the local database with the changed items
                itemsToUpdate.forEach { updatedItem ->
                    setUpBoxContentRepo.update(updatedItem)
                }
            } catch (e: Exception) {
                Log.e("SupabaseError", e.message.toString())
            }
        }
    }





}