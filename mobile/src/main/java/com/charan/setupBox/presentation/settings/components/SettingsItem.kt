package com.charan.setupBox.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SettingsItem(
    icon: ImageVector,
    title : String,
    onClick : () -> Unit

){
    ListItem(
        headlineContent = {
            Text(text = title)
        },
        leadingContent = {
            Icon(icon, contentDescription = title)
        },
        modifier = Modifier.clickable {
            onClick()
        }
    )

}