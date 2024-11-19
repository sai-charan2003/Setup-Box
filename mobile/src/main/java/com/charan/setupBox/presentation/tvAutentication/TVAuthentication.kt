package com.charan.setupBox.presentation.tvAutentication

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun TVAuthentication(
    navHostController: NavHostController,
    viewModel: TVAuthenticationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var code by remember {
        mutableStateOf("")
    }

    Column {
        TextField(value = code, onValueChange = {
            code = it
        }
        )
        Button(onClick = { viewModel.addAuthenticationToken(code, context) }) {
            Text("Enter code")

        }
    }
}