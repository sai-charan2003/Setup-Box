package com.charan.setupBox.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.charan.setupBox.R
import com.charan.setupBox.presentation.navigation.HomeScreenNav
import com.charan.setupBox.presentation.navigation.LoginScreenNav
import com.charan.setupBox.utils.ProcessState

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val googleAuthProcess by viewModel.googleAuthProcess.collectAsState()
    LaunchedEffect(googleAuthProcess) {
        when (googleAuthProcess) {
            is ProcessState.Error -> {
                Toast.makeText(
                    context,
                    (googleAuthProcess as ProcessState.Error).error,
                    Toast.LENGTH_LONG
                ).show()
            }
            ProcessState.Success -> {

                navHostController.navigate(HomeScreenNav(null)) {
                    popUpTo(LoginScreenNav) { inclusive = true }
                }
            }
            else -> {
                Log.d("TAG", "LoginScreen: $googleAuthProcess")
            }
        }
    }


    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = null,
                Modifier.size(100.dp)
            )
            Text(
                text = "Setup Box",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { viewModel.loginWithGoogle(context) },
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                enabled = googleAuthProcess !is ProcessState.Loading
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = com.google.android.gms.base.R.drawable.googleg_standard_color_18),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Continue with Google",
                        modifier = Modifier
                            .animateContentSize()
                            .then(
                                if (googleAuthProcess is ProcessState.Loading) {
                                    Modifier.padding(end = 10.dp)
                                } else {
                                    Modifier
                                }
                            )
                    )
                    AnimatedVisibility(visible = googleAuthProcess is ProcessState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .fillMaxWidth(),

                            strokeCap = StrokeCap.Round,
                            strokeWidth = 3.dp
                        )
                    }
                }

            }
        }
    }


}
