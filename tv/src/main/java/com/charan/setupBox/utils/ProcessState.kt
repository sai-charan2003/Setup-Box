package com.charan.setupBox.utils

sealed class ProcessState {
    data class Success(val data : String? = null): ProcessState()
    data object Loading: ProcessState()
    data class Error(val error:String): ProcessState()
}