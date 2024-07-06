package com.charan.setupBox.utils

sealed class ProcessState {
    object Success: ProcessState()
    object Loading: ProcessState()
    data class Error(val error:String): ProcessState()
}