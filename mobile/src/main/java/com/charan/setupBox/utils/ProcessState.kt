package com.charan.setupBox.utils

sealed class ProcessState {
    data object Success: ProcessState()
    data object Loading: ProcessState()
    data class Error(val error:String): ProcessState()
}