package com.charan.setupBox.presentation.home

import TopBar
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box



import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.charan.setupBox.BuildConfig
import com.charan.setupBox.presentation.ViewModel.HomeViewModel
import com.charan.setupBox.presentation.home.components.AvatarImage
import com.charan.setupBox.presentation.home.components.AvatarImageCard
import com.charan.setupBox.presentation.home.components.ListRow
import com.charan.setupBox.presentation.home.components.TitleText
import com.charan.setupBox.presentation.navigation.HomeScreenNav
import com.charan.setupBox.presentation.navigation.LoginScreenNav

import com.charan.setupBox.utils.AppConstants
import com.charan.setupBox.utils.AppUtils
import com.charan.setupBox.utils.ProcessState
import com.charan.setupBox.utils.SupabaseUtils

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val data = viewModel.allData.collectAsState()
    val newsItems = data.value.filter { it.Category == AppConstants.NEWS }
    val entertainment = data.value.filter { it.Category == AppConstants.ENTERTAINMENT }
    val sport = data.value.filter { it.Category == AppConstants.SPORTS }
    val modalSheetState by viewModel.openModalSheet.collectAsState()
    val logoutState by viewModel.logoutState.collectAsState()
    LaunchedEffect(key1 = logoutState) {
        when(logoutState){
            is ProcessState.Error -> {
                Toast.makeText(context, (logoutState as ProcessState.Error).error,Toast.LENGTH_LONG).show()
            }

            is ProcessState.Success -> {
                navHostController.navigate(LoginScreenNav) {
                    popUpTo(HomeScreenNav) {
                        inclusive = true
                    }
                }

            }
           else -> Unit
        }

    }
    val focusRequester = remember { FocusRequester() }
    BackHandler(modalSheetState) {
        viewModel.modalSheetState()
    }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        

        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(0.dp),
            reverseLayout = false,
            horizontalAlignment = Alignment.Start,
            userScrollEnabled = true
        ) {
            item {
                TopBar(
                    onRefresh = {
                        viewModel.getSupabaseData()
                    },
                    onOpen = {
                        viewModel.modalSheetState()
                    }
                )
            }
            item { TitleText(AppConstants.NEWS) }
            item {
                if (newsItems.isNotEmpty()) {
                    ListRow(
                        items = newsItems,
                        onClick = { item ->
                            AppUtils.openLink(context, item.app_Package!!, item.channelLink!!)
                        },
                        shouldRequestFocus = true
                    )
                }
            }
            item { TitleText(title = AppConstants.ENTERTAINMENT) }
            item {
                if (entertainment.isNotEmpty()) {
                    ListRow(
                        items = entertainment,
                        onClick = { item ->
                            AppUtils.openLink(context, item.app_Package!!, item.channelLink!!)
                        }
                    )
                }
            }
            item { TitleText(AppConstants.SPORTS) }
            item {
                if (sport.isNotEmpty()) {
                    ListRow(
                        items = sport,
                        onClick = { item ->
                            AppUtils.openLink(context, item.app_Package!!, item.channelLink!!)
                        }
                    )
                }
            }
        }

        
        if (modalSheetState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { viewModel.modalSheetState() }
            )


            AnimatedVisibility(
                visible = modalSheetState,
                enter = slideInHorizontally(initialOffsetX = { it }),
                exit = slideOutHorizontally(targetOffsetX = { it }),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                ModalDrawerContent(
                    focusRequester,
                    onLogout = {
                        viewModel.logout()
                    },
                    isLogingOut = logoutState is ProcessState.Loading
                )
            }
            LaunchedEffect(modalSheetState) {
                if (modalSheetState) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}

@Composable
fun ModalDrawerContent(
    focusRequester: FocusRequester,
    onLogout : () -> Unit,
    isLogingOut : Boolean
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .padding(5.dp)
            .clip(RoundedCornerShape(16.dp))

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(15.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarImage(imageUrl = SupabaseUtils.getProfilePic())
                    Text(
                        text = SupabaseUtils.getUserName() ?: SupabaseUtils.getEmail().toString(),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                ListItem(
                    enabled = !isLogingOut,
                    selected = true,
                    onClick = { onLogout() },
                    modifier = Modifier.focusRequester(focusRequester),
                    headlineContent = { Text(text = "Logout") },
                    leadingContent = {
                        Icon(Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = "Logout")
                    },
                    trailingContent = {
                        AnimatedVisibility(visible = isLogingOut) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeCap = StrokeCap.Round)
                            
                        }
                    }

                )
            }
        }

        
        Text(
            text = "Setup Box ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}


