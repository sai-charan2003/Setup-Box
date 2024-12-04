package com.charan.setupBox.presentation.addChannel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.local.entity.SetupBoxContent
import com.charan.setupBox.data.repository.SetUpBoxRepo
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.utils.AddChannelState
import com.charan.setupBox.utils.ProcessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class AddDataViewModel @Inject constructor(
    private val supabaseRepo: SupabaseRepo,
    private val setUpBoxRepo: SetUpBoxRepo
): ViewModel() {

    private val _uiState = MutableStateFlow(AddChannelState())
    val uiState = _uiState.asStateFlow()

    private val _saveDataState = MutableStateFlow<ProcessState?>(null)
    val saveDataState = _saveDataState.asStateFlow()

    private val _deleteState = MutableStateFlow<ProcessState?>(null)
    val deleteState = _deleteState.asStateFlow()

    fun initializeChannel(id: Int?,sharedURL : String?) {
        if(sharedURL.isNullOrEmpty().not()){
            _uiState.value=_uiState.value.copy(channelLink = sharedURL!!)
        }
        if (id != null && id != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                val data = setUpBoxRepo.getDataById(id)
                _uiState.value = _uiState.value.copy(
                    title = "Edit Channel",
                    channelName = data.channelName ?: "",
                    channelLink = data.channelLink ?: "",
                    channelPhoto = data.channelPhoto ?: "",
                    packageName = data.app_Package ?: "",
                    category = data.Category ?: "",
                    uuid = data.uuid ?: ""
                )

            }
        }
        loadDistinctPackages()
    }

    private fun loadDistinctPackages() {
        viewModelScope.launch (Dispatchers.IO){
            val packages = setUpBoxRepo.selectDistinctAppPackage()
            _uiState.update { it.copy(distinctAppPackages = packages) }
        }
    }

    fun updateChannelName(name: String) {
        _uiState.value = _uiState.value.copy(channelName = name)
    }

    fun updateChannelLink(link: String) {
        _uiState.value = _uiState.value.copy(channelLink = link)
    }

    fun updateChannelPhoto(photo: String) {
        _uiState.value = _uiState.value.copy(channelPhoto = photo)
    }

    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun toggleDropdown() {
        _uiState.update { it.copy(showDropdown = !it.showDropdown) }
    }

    fun togglePreviewBox() {
        _uiState.update { it.copy(showPreviewAlertBox = !it.showPreviewAlertBox) }
    }

    fun updatePackageName(packageName: String) {
        _uiState.update { it.copy(packageName = packageName) }
    }

    fun saveData(){
        _saveDataState.tryEmit(ProcessState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _uiState.value
            supabaseRepo.insertData(
                SetupBoxContent(
                    channelName = currentState.channelName,
                    channelLink = currentState.channelLink,
                    channelPhoto = currentState.channelPhoto,
                    Category = currentState.category,
                    app_Package = currentState.packageName,
                )
            ).collectLatest {
                _saveDataState.tryEmit(it)
            }
        }
    }

    fun updateData() {
        _saveDataState.tryEmit(ProcessState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _uiState.value
            supabaseRepo.updateData(
                SetupBoxContent(
                    channelName = currentState.channelName,
                    channelLink = currentState.channelLink,
                    channelPhoto = currentState.channelPhoto,
                    Category = currentState.category,
                    app_Package = currentState.packageName,
                    uuid = _uiState.value.uuid
                )
            ).collectLatest {
                _saveDataState.tryEmit(it)
            }

        }
    }

    fun deleteData(uuid : String){
        _deleteState.tryEmit(ProcessState.Loading)
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.deleteData(uuid).collectLatest {
                _deleteState.tryEmit(it)
            }
        }
    }

    fun isValid(): Boolean {
        val currentState = _uiState.value
        val errorMessages = mutableListOf<String>()


        if (currentState.channelName.isBlank()) {
            errorMessages.add("Channel Name cannot be empty.")
        }
        if (currentState.channelLink.isBlank()) {
            errorMessages.add("Channel Link cannot be empty.")
        }
        if (currentState.packageName.isBlank()) {
            errorMessages.add("Package Name cannot be empty.")
        }
        if (currentState.category.isBlank()) {
            errorMessages.add("Category cannot be empty.")
        }


        val finalErrorMessage = if (errorMessages.isNotEmpty()) {
            errorMessages.joinToString("\n")
        } else {
            null
        }


        _uiState.update { it.copy(errors = finalErrorMessage) }



        return errorMessages.isEmpty()
    }





}