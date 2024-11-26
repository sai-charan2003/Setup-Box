package com.charan.setupBox.presentation.login.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun CodeLabel(
    code: String?,
    modifier: Modifier = Modifier,
    isGenerating: Boolean
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(20.dp))

    ) {
        if (isGenerating) {
            CircularProgressIndicator(
                modifier = Modifier.padding(20.dp),
                strokeCap = StrokeCap.Round
            )
        } else {
            Text(
                text = code ?: "loading",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}
