package com.charan.setupBox.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.data.repository.SetUpBoxRepo
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val supabaseRepo: SupabaseRepo,
    private val setUpBoxRepo: SetUpBoxRepo
) : ViewModel() {

    private val _allData = MutableStateFlow(emptyList<SetupBoxContent>())
    val allData = _allData.asStateFlow()

    init {

        viewModelScope.launch {
            setUpBoxRepo.getAllData().collectLatest {
                _allData.tryEmit(it)
            }
        }

    }

    fun insert(setupBoxContent: SetupBoxContent)=viewModelScope.launch(Dispatchers.IO){
        setUpBoxRepo.insert(setupBoxContent)
    }
    fun update(setupBoxContent: SetupBoxContent)=viewModelScope.launch(Dispatchers.IO) {
        setUpBoxRepo.update(setupBoxContent)
    }

    fun deleteById(id:Int)=viewModelScope.launch(Dispatchers.IO) {
        setUpBoxRepo.deleteById(id)
    }

    fun getDataById(id:Int): SetupBoxContent {
       return  setUpBoxRepo.getDataById(id)
    }

    fun selectDistinctAppPackage():List<String?>{
        return setUpBoxRepo.selectDistinctAppPackage()
    }

    fun getSupabaseData(): LiveData<ProcessState?> {
        val processState = MediatorLiveData<ProcessState?>()
        processState.value = ProcessState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val repoLiveData = supabaseRepo.getData()
            withContext(Dispatchers.Main) {
                processState.addSource(repoLiveData) { data ->
                    processState.value = data
                }
            }
        }

        return processState
    }




    fun insertDataIntoSupabase(setupBoxContent: SetupBoxContent): LiveData<ProcessState?>{
        val processState = MediatorLiveData<ProcessState?>()
        processState.value = ProcessState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val repoLiveData = supabaseRepo.insertData(setupBoxContent)
            withContext(Dispatchers.Main) {
                processState.addSource(repoLiveData) { data ->
                    processState.value = data
                }
            }
        }

        return processState
    }

    fun updateSupabase(setupBoxContent: SetupBoxContent): LiveData<ProcessState?>{
        val processState = MediatorLiveData<ProcessState?>()
        processState.value = ProcessState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val repoLiveData = supabaseRepo.updateData(setupBoxContent)
            withContext(Dispatchers.Main) {
                processState.addSource(repoLiveData) { data ->
                    processState.value = data
                }
            }
        }

        return processState

    }

    fun deleteSupabase(id:Int) : LiveData<ProcessState?>{
        val processState = MediatorLiveData<ProcessState?>()
        processState.value = ProcessState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val repoLiveData = supabaseRepo.deleteData(id)
            withContext(Dispatchers.Main) {
                processState.addSource(repoLiveData) { data ->
                    processState.value = data
                }
            }
        }

        return processState

    }




}