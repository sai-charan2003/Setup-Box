package com.charan.setupBox.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.Database.AppDatabase
import com.charan.setupBox.Database.SetUpBoxContentRepo
import com.charan.setupBox.Database.SetUpBoxImp
import com.charan.setupBox.Database.SetupBoxContent
import com.charan.setupBox.SupaBase.supabaseClient
import com.charan.setupBox.utils.ProcessState
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

    fun insert(setupBoxContent: SetupBoxContent)=viewModelScope.launch(Dispatchers.IO){
        setUpBoxContentRepo.insert(setupBoxContent)
    }
    fun update(setupBoxContent: SetupBoxContent)=viewModelScope.launch(Dispatchers.IO) {
        setUpBoxContentRepo.Update(setupBoxContent)
    }

    fun deleteById(id:Int)=viewModelScope.launch(Dispatchers.IO) {
        setUpBoxContentRepo.deleteDataById(id)
    }

    fun getDataById(id:Int):SetupBoxContent{
       return  setUpBoxContentRepo.getDataById(id)
    }

    fun selectDistinctAppPackage():List<String?>{
        return setUpBoxContentRepo.selectDistinctAppPackage()
    }

    fun getSupabaseData(): LiveData<ProcessState>{
        val processState= MutableLiveData<ProcessState>(ProcessState.Loading)
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
                processState.postValue(ProcessState.Success)
            } catch (e:Exception){
                Log.e("SupabaseError",e.message.toString())
                processState.postValue(ProcessState.Error(e.message.toString()))
            }

        }
        return processState
    }

    fun insertDataIntoSupabase(setupBoxContent: SetupBoxContent): LiveData<ProcessState>{
        val processState= MutableLiveData<ProcessState>(ProcessState.Loading)
        viewModelScope.launch {

            try{
                supabaseClient.client.from("SetupBoxContent").insert(SetupBoxContent(
                    id=null,
                    channelLink = setupBoxContent.channelLink,
                    channelName = setupBoxContent.channelName,
                    channelPhoto = setupBoxContent.channelPhoto,
                    Category = setupBoxContent.Category,
                    app_Package = setupBoxContent.app_Package
                ))
                insert(setupBoxContent)
                processState.postValue(ProcessState.Success)

            } catch (e:Exception){
                processState.postValue(ProcessState.Error(e.message.toString()))
                Log.e("SupabaseError",e.message.toString())
            }
        }
        return processState
    }

    fun updateSupabase(setupBoxContent: SetupBoxContent): LiveData<ProcessState>{
        val processState= MutableLiveData<ProcessState>(ProcessState.Loading)
        viewModelScope.launch {
            try{
                supabaseClient.client
                    .from("SetupBoxContent")
                    .update(
                        {
                            set("channelLink",setupBoxContent.channelLink)
                            set("channelName",setupBoxContent.channelName)
                            set("channelPhoto",setupBoxContent.channelPhoto)
                            set("Category",setupBoxContent.Category)
                            set("app_Package",setupBoxContent.app_Package)
                        }
                    ) {
                        filter {
                            eq("id",setupBoxContent.id!!)
                        }
                    }
                update(setupBoxContent)
                processState.postValue(ProcessState.Success)

            } catch (e:Exception){
                processState.postValue(ProcessState.Error(e.message.toString()))
                Log.e("SupabaseError",e.message.toString())
            }

        }
        return processState
    }

    fun deleteSupabase(id:Int) : LiveData<ProcessState>{
        val processState= MutableLiveData<ProcessState>(ProcessState.Loading)
        viewModelScope.launch {
            try{
                supabaseClient.client
                    .from("SetupBoxContent")
                    .delete{
                        filter {
                            eq("id",id)

                        }

                    }



                deleteById(id)
                processState.postValue(ProcessState.Success)

            } catch (e:Exception){
                processState.postValue(ProcessState.Error(e.message.toString()))
                Log.e("SupabaseError",e.message.toString())
            }

        }
        return processState

    }




}