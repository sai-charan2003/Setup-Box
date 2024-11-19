package com.charan.setupBox.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.charan.setupBox.R
import com.charan.setupBox.presentation.login.components.CodeLabel
import com.charan.setupBox.presentation.navigation.HomeScreenNav
import com.charan.setupBox.utils.LoginState
import com.charan.setupBox.utils.ProcessState

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun OTPScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    emailId : String
){
    val context = LocalContext.current
    var otp by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    val authenticationStatus by viewModel.authenticationStatus.collectAsState()
    val loginState by viewModel.loginState.collectAsState()
    LaunchedEffect(key1 = loginState) {
        when(loginState){
            LoginState.Loading -> {

            }

            is LoginState.OTPVerificationError -> {
                Toast.makeText(context, (loginState as LoginState.OTPVerificationError).error,Toast.LENGTH_LONG).show()

            }
            LoginState.OTPVerified -> {
                viewModel.authenticationStatus()
            }
            else -> {

            }
        }

    }
    LaunchedEffect(key1 = authenticationStatus) {
        when(authenticationStatus){
            is ProcessState.Error -> {
                isLoading = false
                Toast.makeText(context,(authenticationStatus as ProcessState.Error).error,Toast.LENGTH_LONG).show()

            }
            ProcessState.Loading -> {
                isLoading = true

            }
            is ProcessState.Success -> {
                isLoading = false
                navHostController.navigate(HomeScreenNav)
            }
            null -> {

            }
        }

    }
    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color.Black), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = "appIcon",modifier=Modifier.size(100.dp))
        BasicTextField(
            value = otp,
            onValueChange = {otp = it},
            modifier = Modifier
                .fillMaxWidth()
                .background(


                        MaterialTheme.colorScheme.surfaceVariant,

                )
                .border(
                    width = 2.dp,
                    color = Color.White

                )
                .padding(16.dp)
                .focusRequester(remember { FocusRequester() }),


            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
        )
        Button(onClick = { viewModel.verifyOTPStatus(emailId,otp) }, enabled = !isLoading) {
            Text(text = "Login")

        }
    }

}