package com.charan.setupBox.presentation.addChannel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.charan.setupBox.presentation.addChannel.components.CustomTextField
import com.charan.setupBox.presentation.addChannel.components.CustomTextForPackages

import com.charan.setupBox.presentation.addChannel.components.PreviewAlertBox
import com.charan.setupBox.utils.ProcessState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewChannel(
    navHostController: NavHostController,
    id:Int?,
    sharedURL : String?,
    viewModel: AddDataViewModel = hiltViewModel()
) {
    val scroll = TopAppBarDefaults.pinnedScrollBehavior()

    val context= LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.initializeChannel(id,sharedURL)
    }
    val uiState by viewModel.uiState.collectAsState()
    val saveState by viewModel.saveDataState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()
    LaunchedEffect(key1 = deleteState) {
        when(deleteState){
            is ProcessState.Error -> {
                Toast.makeText(context, (deleteState as ProcessState.Error).error,Toast.LENGTH_LONG).show()
            }

            ProcessState.Success -> {
                navHostController.popBackStack()
            }
            else -> Unit

        }


    }
    LaunchedEffect(uiState.errors) {
        uiState.errors?.let { errors ->
            Toast.makeText(context, errors, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(key1 = saveState) {
        when(saveState){
            is ProcessState.Error -> {
                Toast.makeText(context, (deleteState as ProcessState.Error).error,Toast.LENGTH_LONG).show()
            }

            ProcessState.Success -> {
                navHostController.popBackStack()
                Toast.makeText(context,"Channel saved",Toast.LENGTH_LONG).show()
            }
            else -> Unit

        }


    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scroll.nestedScrollConnection),
        {
            TopAppBar(
                title = { Text(uiState.title) },
                scrollBehavior = scroll,
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,null)

                    }
                },

                actions = {
                    if(id!=-1) {
                        IconButton(onClick = {
                            viewModel.deleteData(uiState.uuid)

                        })
                         {
                            if(deleteState is ProcessState.Loading){
                                CircularProgressIndicator()
                            }
                            Icon(Icons.Filled.Delete, null)
                        }
                    }
                }

                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(viewModel.isValid()) {
                    if (id == -1) {
                        viewModel.saveData()
                    } else {
                        viewModel.updateData()
                    }
                }

            }) {
                if(saveState is ProcessState.Loading){
                    CircularProgressIndicator(strokeCap = StrokeCap.Round,modifier=Modifier.size(24.dp))
                } else {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                }

            }
        }
    ){
        LazyColumn(modifier= Modifier
            .fillMaxSize()
            .padding(it)) {
            item {

                CustomTextField(
                    value = uiState.channelName,
                    onValueChange = {
                                    viewModel.updateChannelName(it)
                    },
                    modifier = Modifier.padding(top=20.dp, start = 10.dp,end=10.dp),
                    label = "Channel Name")

                CustomTextField(
                    value = uiState.channelLink,
                    onValueChange = {
                        viewModel.updateChannelLink(it)
                    },
                    modifier = Modifier.padding(top=25.dp,start = 10.dp,end=10.dp),
                    label = "Channel Link")


                    CustomTextField(
                        value = uiState.channelPhoto,
                        onValueChange = {
                            viewModel.updateChannelPhoto(it)
                        },
                        modifier = Modifier

                            .padding(top=25.dp,start = 10.dp,end=10.dp),
                        label = "Channel Photo Link",
                        showButton = true,
                        icon = Icons.Default.Preview,
                        isButtonEnabled = uiState.channelPhoto.isNotEmpty(),
                        onClick = {
                            viewModel.togglePreviewBox()

                        }
                    )

                CustomTextForPackages(
                    value = uiState.packageName,
                    onValueChange = {
                        viewModel.updatePackageName(it)
                    },
                    modifier = Modifier.padding(top=25.dp,start = 10.dp,end=10.dp),
                    label = "Package Name",
                    packages= uiState.distinctAppPackages
                )

                
                ListItem({
                    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                        Text(text = "Select Category",modifier=Modifier.padding(top=10.dp),)
                        Spacer(Modifier.weight(1f))

                        TextButton(onClick = {viewModel.toggleDropdown()}, contentPadding = PaddingValues(3.dp)) {
                            Text(uiState.category)
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                            MaterialTheme(
                                shapes = MaterialTheme.shapes.copy(
                                    extraSmall = RoundedCornerShape(
                                        16.dp
                                    )
                                )
                            ) {
                                DropdownMenu(
                                    expanded = uiState.showDropdown,
                                    onDismissRequest = { viewModel.toggleDropdown() },

                                    ) {
                                    Categories.entries.forEach {
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    it.name,
                                                    textAlign = TextAlign.Center
                                                )
                                            },
                                            onClick = {
                                                 viewModel.updateCategory(it.name)
                                                viewModel.toggleDropdown()


                                            },
                                            modifier = if (uiState.category == it.name) {
                                                Modifier.background(
                                                    MaterialTheme.colorScheme.surfaceTint.copy(
                                                        alpha = 0.3f
                                                    )
                                                )
                                            } else {
                                                Modifier
                                            }


                                        )

                                    }
                                }
                            }
                        }



                    }


                },
                    modifier= Modifier
                        .padding(top = 25.dp)
                        .clickable { viewModel.toggleDropdown() }
                )
            }



        }
    }

    if(uiState.showPreviewAlertBox){
        PreviewAlertBox(imageLink = uiState.channelPhoto) {
            viewModel.togglePreviewBox()

        }
    }

}
enum class Categories{
    NEWS,
    ENTERTAINMENT,
    SPORTS
}

