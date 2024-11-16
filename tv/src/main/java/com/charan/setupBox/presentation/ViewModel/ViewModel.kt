package com.charan.setupBox.presentation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.repository.SetUpBoxContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val supabaseRepo: SupabaseRepo,private val setUpBoxContentRepository: SetUpBoxContentRepository): ViewModel() {

    private val _allData = MutableStateFlow(emptyList<SetupBoxContent>())
    val allData = _allData.asStateFlow()

    init {
        viewModelScope.launch {
            setUpBoxContentRepository.getAllData().collectLatest {
                _allData.tryEmit(it)
            }
        }

    }

    private fun insert(setupBoxContent: SetupBoxContent)=viewModelScope.launch(Dispatchers.IO){
        setUpBoxContentRepository.insert(setupBoxContent)
    }

    fun getSupabaseData() = viewModelScope.launch (Dispatchers.IO){
        supabaseRepo.getDataFromSupabase()
    }





}