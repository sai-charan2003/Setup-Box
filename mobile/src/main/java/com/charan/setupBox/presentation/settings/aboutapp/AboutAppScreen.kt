package com.charan.setupBox.presentation.settings.aboutapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.charan.setupBox.BuildConfig
import com.charan.setupBox.presentation.navigation.LicenseScreenNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(navHostController: NavHostController){
    val scroll = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("About App") },
                scrollBehavior = scroll
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .nestedScroll(scroll.nestedScrollConnection)
        ) {
            item {
                ListItem(
                    headlineContent = { Text(text = "App Version") },
                    supportingContent = { Text(BuildConfig.VERSION_NAME)}
                )
                ListItem(
                    headlineContent = { Text(text = "Project on Github") },
                    modifier = Modifier.clickable {

                    }
                )
                ListItem(
                    headlineContent = { Text("Licenses") },
                    modifier = Modifier.clickable {
                        navHostController.navigate(LicenseScreenNav)
                    }
                )
            }

        }

    }

}