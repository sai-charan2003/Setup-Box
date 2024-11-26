package com.charan.setupBox.presentation.home

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.charan.setupBox.presentation.navigation.AddNewChannelScreenNav
import com.charan.setupBox.presentation.navigation.SettingsScreenNav
import com.charan.setupBox.presentation.navigation.TVAuthenticationNav

import com.charan.setupBox.utils.ProcessState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    sharedURL : String?,
    homeViewModel : HomeViewModel = hiltViewModel()
) {
    var channelLink by rememberSaveable {
        mutableStateOf(sharedURL)

    }

    val scroll = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isRefreshing by homeViewModel.refreshState.collectAsState()

    val showDropDown by homeViewModel.showDropDownMenu.collectAsState()

    
    LaunchedEffect(key1 = Unit) {
        if(channelLink.isNullOrEmpty().not()){
            val id = -1
            navHostController.navigate(AddNewChannelScreenNav(id = id, channelLink = channelLink))
            channelLink = null
        }
    }
    val allData=homeViewModel.allData.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scroll.nestedScrollConnection),
        {
            LargeTopAppBar(
                title = { Text("Setup Box") },
                scrollBehavior = scroll,
                actions = {
                    IconButton(onClick = { homeViewModel.showDropDownMenu() }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More" )

                    }
                    DropdownMenu(
                        expanded = showDropDown,
                        onDismissRequest = { homeViewModel.hideDropDownMenu() }) {
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = { navHostController.navigate(SettingsScreenNav);homeViewModel.hideDropDownMenu() })
                        
                    }

                }

            )

        },
        floatingActionButton = {
            val id=-1
            FloatingActionButton(onClick = { navHostController.navigate(AddNewChannelScreenNav(id = id, channelLink = null)) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)

            }
        }
    ){
        PullToRefreshBox(
            isRefreshing = isRefreshing is ProcessState.Loading,
            onRefresh = {
                homeViewModel.getSupabaseData()
            },
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()


                ) {
                    items(allData.value.size) { item ->
                        ListItem(
                            {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 20.dp, bottom = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = allData.value[item].channelPhoto,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(71.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    )
                                    Text(
                                        allData.value[item].channelName.toString(),
                                        modifier = Modifier.padding(10.dp),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }

                            },
                            modifier = Modifier.clickable {
                                navHostController.navigate(
                                    AddNewChannelScreenNav(
                                        id = allData.value[item].id,
                                        channelLink = null
                                    )
                                )
                            }
                        )
                        HorizontalDivider(modifier = Modifier)

                    }


                }



            }
        }
    }






}