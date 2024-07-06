package com.charan.setupBox.Screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight


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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.charan.setupBox.Screens.components.CustomTextField
import com.charan.setupBox.Screens.components.CustomTextForPackages
import com.charan.setupBox.Database.SetupBoxContent
import com.charan.setupBox.Screens.components.PreviewAlertBox
import com.charan.setupBox.utils.ProcessState
import com.charan.setupBox.viewModel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewChannel(navHostController: NavHostController,id:Int?) {
    val application = LocalContext.current.applicationContext as Application
    val scroll = TopAppBarDefaults.pinnedScrollBehavior()
    val viewModel = viewModel<ViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return ViewModel(
                    application
                ) as T
            }
        }

    )
    var title by remember {
        mutableStateOf("Add Channel")
    }
    var isDeleting by remember {
        mutableStateOf(false)
    }
    val coroutine= rememberCoroutineScope()
    var channelName by remember {
        mutableStateOf("")
    }
    var channelLink by remember {
        mutableStateOf("")
    }
    var packageName by remember {
        mutableStateOf("")
    }
    var channelPhoto by remember {
        mutableStateOf("")
    }
    var showDropdown by remember {
        mutableStateOf(false)
    }
    var category by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var distinctAppPackage by remember {
        mutableStateOf<List<String?>>(emptyList<String?>())
    }
    var showPreviewAlertBox by remember {
        mutableStateOf(false)
    }
    var context= LocalContext.current
    val haptic= LocalHapticFeedback.current
    if(id!=-1){
        LaunchedEffect(key1 = Unit) {
            launch(Dispatchers.IO) {
                title="Edit Channel"
                val data = viewModel.getDataById(id!!)
                channelName = data.channelName.toString()
                channelLink = data.channelLink.toString()
                packageName = data.app_Package.toString()
                channelPhoto = data.channelPhoto.toString()
                category = data.Category.toString()
            }



        }


    }

    val lifecycle= LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getSupabaseData()
        launch (Dispatchers.IO){
            distinctAppPackage=viewModel.selectDistinctAppPackage()
        }
    }
    val allData=viewModel.allData.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scroll.nestedScrollConnection),
        {
            TopAppBar(
                title = { Text(title) },
                scrollBehavior = scroll,
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,null)

                    }
                },

                actions = {
                    if(id!=-1) {
                        IconButton(onClick = { viewModel.deleteSupabase(id!!).observe(lifecycle){
                            when(it){
                                is ProcessState.Error -> {
                                    isDeleting=false
                                    Toast.makeText(context,it.error,Toast.LENGTH_LONG).show()
                                }
                                ProcessState.Loading -> {
                                    isDeleting=true

                                }
                                ProcessState.Success -> {
                                    isDeleting=false
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    navHostController.popBackStack()

                                }
                            }
                        } }) {

                            if(isDeleting){
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
                coroutine.launch {
                    if(id==-1) {
                        viewModel.insertDataIntoSupabase(
                            SetupBoxContent(
                                id = null,
                                channelLink,
                                channelName,
                                channelPhoto,
                                Category = category,
                                packageName
                            )
                        ).observe(lifecycle){
                            when(it){
                                is ProcessState.Error -> {
                                    isLoading=false
                                    Toast.makeText(context,it.error,Toast.LENGTH_LONG).show()
                                }
                                ProcessState.Loading -> {
                                    isLoading=true

                                }
                                ProcessState.Success -> {
                                    isLoading=false
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    navHostController.popBackStack()

                                }
                            }
                        }
                    } else{
                        viewModel.updateSupabase(
                            SetupBoxContent(
                                id =  id ,
                                channelLink,
                                channelName,
                                channelPhoto,
                                Category = category,
                                packageName
                            )
                        ).observe(lifecycle){
                            when(it){
                                is ProcessState.Error -> {
                                    isLoading=false
                                    Toast.makeText(context,it.error,Toast.LENGTH_LONG).show()
                                }
                                ProcessState.Loading -> {
                                    isLoading=true

                                }
                                ProcessState.Success -> {
                                    isLoading=false
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    navHostController.popBackStack()

                                }
                            }
                        }
                    }
                }
            }) {
                if(isLoading){
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
                    value = channelName,
                    onValueChange = {
                                    channelName=it
                    },
                    modifier = Modifier.padding(top=20.dp, start = 10.dp,end=10.dp),
                    label = "Channel Name")

                CustomTextField(
                    value = channelLink,
                    onValueChange = {
                        channelLink=it
                    },
                    modifier = Modifier.padding(top=25.dp,start = 10.dp,end=10.dp),
                    label = "Channel Link")


                    CustomTextField(
                        value = channelPhoto,
                        onValueChange = {
                            channelPhoto = it
                        },
                        modifier = Modifier

                            .padding(top=25.dp,start = 10.dp,end=10.dp),
                        label = "Channel Photo Link",
                        showButton = true,
                        icon = Icons.Default.Preview,
                        isButtonEnabled = channelPhoto.isNotEmpty(),
                        onClick = {
                            showPreviewAlertBox=true

                        }
                    )






                CustomTextForPackages(
                    value = packageName,
                    onValueChange = {
                        packageName=it
                    },
                    modifier = Modifier.padding(top=25.dp,start = 10.dp,end=10.dp),
                    label = "Package Name",
                    packages=distinctAppPackage
                    )

                
                ListItem({
                    Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                        Text(text = "Select Category",modifier=Modifier.padding(top=10.dp),)
                        Spacer(Modifier.weight(1f))

                        TextButton(onClick = {showDropdown=true}, contentPadding = PaddingValues(3.dp)) {
                            Text(category)
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                            MaterialTheme(
                                shapes = MaterialTheme.shapes.copy(
                                    extraSmall = RoundedCornerShape(
                                        16.dp
                                    )
                                )
                            ) {
                                DropdownMenu(
                                    expanded = showDropdown,
                                    onDismissRequest = { showDropdown = false },

                                    ) {
                                    Categories.values().forEach {

                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    it.name,
                                                    textAlign = TextAlign.Center
                                                )
                                            },
                                            onClick = {
                                                category = it.name
                                                showDropdown = false

                                            },
                                            modifier = if (category == it.name) {
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
                        .clickable { showDropdown = true }
                )
            }



        }
    }

    if(showPreviewAlertBox){
        PreviewAlertBox(imageLink = channelPhoto) {
            showPreviewAlertBox=false

        }
    }

}
enum class Categories{
    NEWS,
    ENTERTAINMENT,
    SPORTS
}

