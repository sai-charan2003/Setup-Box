package com.charan.setupBox.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.Card
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SettingsScreen(navHostController: NavHostController) {
    // Modal-like UI container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        AnimatedContent(
            targetState = true,
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { it }) with
                        slideOutHorizontally(targetOffsetX = { it })
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight(),

                onClick = {

                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    // Add settings items here
                    Text(text = "Option 1")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Option 2")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Option 3")
                }
            }
        }
    }
}
