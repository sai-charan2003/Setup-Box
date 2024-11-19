package com.charan.setupBox.presentation.login.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun CodeLabel(code:String,modifier: Modifier){
    Box(
        modifier = Modifier
            .border(width = 2.dp, color = Color.White)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Text(
            text = code,
            style = MaterialTheme.typography.displayMedium,
            modifier= Modifier.padding(10.dp)
            )
    }
}