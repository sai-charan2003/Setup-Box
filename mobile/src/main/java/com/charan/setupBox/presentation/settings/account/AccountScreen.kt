package com.charan.setupBox.presentation.settings.account

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.charan.setupBox.presentation.navigation.HomeScreenNav
import com.charan.setupBox.presentation.navigation.LoginScreenNav
import com.charan.setupBox.presentation.settings.SettingsViewModel
import com.charan.setupBox.utils.ProcessState
import com.charan.setupBox.utils.SupabaseUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navHostController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
    ){
    val logoutStatus by viewModel.logoutStatus.collectAsState()
    val bottomSheetStatus by viewModel.bottomSheetStatus.collectAsState()
    val textFieldValue by viewModel.textFieldValue.collectAsState()
    val authenticationStatus by viewModel.authenticationStatus.collectAsState()
    val scroll = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current
    LaunchedEffect(key1 = logoutStatus) {
        when(logoutStatus){
            is ProcessState.Error -> {
                Toast.makeText(context, (logoutStatus as ProcessState.Error).error,Toast.LENGTH_LONG).show()
            }

            ProcessState.Success -> {

                navHostController.navigate(LoginScreenNav) {
                    popUpTo(HomeScreenNav(null)) {
                        inclusive = true
                    }
                }

            }
            else -> Unit

        }

    }
    LaunchedEffect(key1 = authenticationStatus) {
        when(authenticationStatus){
            is ProcessState.Error -> {
                Toast.makeText(context, (logoutStatus as ProcessState.Error).error,Toast.LENGTH_LONG).show()
            }

            ProcessState.Success -> {
                viewModel.hideBottomSheet()
                viewModel.resetAuthenticationCode()
            }
            else -> Unit
        }

    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Account") },
                scrollBehavior = scroll
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it).nestedScroll(scroll.nestedScrollConnection)) {
            item {
                ListItem(headlineContent = { Text(text = SupabaseUtils.getEmail() ?: "null") })
                ListItem(
                    headlineContent = { Text(text = "Logout") },
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clickable {
                            viewModel.logout()
                        },
                    trailingContent = {
                        AnimatedVisibility(visible = logoutStatus is ProcessState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(20.dp)
                                    .fillMaxWidth(),
                                strokeCap = StrokeCap.Round,
                                strokeWidth = 3.dp
                            )
                        }

                    }

                )
                ListItem(
                    headlineContent = { Text(text = "Authenticate TV") },
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .clickable {
                            viewModel.showModalBottomSheet()
                        },
                )
            }

        }

    }

    if(bottomSheetStatus){
        ModalBottomSheet(onDismissRequest = { viewModel.hideBottomSheet() }) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Enter Code",style= MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {
                        viewModel.authenticateCode(it)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Button(
                    onClick = { viewModel.addAuthenticationToken(context) },
                    enabled = authenticationStatus !is ProcessState.Loading,
                    modifier = Modifier.fillMaxWidth().padding(10.dp)

                )
                {
                    Text(text = "Authenticate",
                        modifier = Modifier
                            .animateContentSize()
                            .then(
                                if (authenticationStatus is ProcessState.Loading) {
                                    Modifier.padding(end = 10.dp)
                                } else {
                                    Modifier
                                }
                            )
                    )
                    AnimatedVisibility(visible = authenticationStatus is ProcessState.Loading) {
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