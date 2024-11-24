package com.charan.setupBox.presentation.tvAutentication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.setupBox.data.repository.SupabaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TVAuthenticationViewModel @Inject constructor(private val supabaseRepo: SupabaseRepo) : ViewModel(){


    fun addAuthenticationToken(code : String,context: Context){
        viewModelScope.launch (Dispatchers.IO){
            supabaseRepo.attachEmailIdToCode(code,context)
        }


    }
}