package com.charan.setupBox.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
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
class HomeViewModel @Inject constructor(
    private val supabaseRepo: SupabaseRepo,
    private val setUpBoxRepo: SetUpBoxRepo
) : ViewModel() {

    private val _allData = MutableStateFlow(emptyList<SetupBoxContent>())
    val allData = _allData.asStateFlow()
    private val _showDropDownMenu = MutableStateFlow(false)
    val showDropDownMenu = _showDropDownMenu.asStateFlow()

    private val _refreshState = MutableStateFlow<ProcessState?>(null)
    val refreshState = _refreshState.asStateFlow()

    init {

        viewModelScope.launch {
            setUpBoxRepo.getAllData().collectLatest {
                _allData.tryEmit(it)
            }
        }
        getSupabaseData()

    }


    fun getSupabaseData(){

        _refreshState.tryEmit(ProcessState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            supabaseRepo.getData().collectLatest {
                _refreshState.tryEmit(it)
            }

        }


    }


    fun showDropDownMenu(){
        _showDropDownMenu.value = true
    }

    fun hideDropDownMenu(){
        _showDropDownMenu.value = false
    }




}