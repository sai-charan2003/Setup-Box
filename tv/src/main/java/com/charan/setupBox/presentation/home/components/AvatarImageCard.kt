package com.charan.setupBox.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage

@Composable
fun AvatarImageCard(
    imageUrl : String?,
    onPress : ()->Unit,

){
    Card(
        onClick = {onPress()},
        modifier = Modifier.aspectRatio(CardDefaults. SquareImageAspectRatio).padding(10.dp),
        border =CardDefaults.border(focusedBorder = Border(border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.onSurface))),
        shape =CardDefaults. shape(shape = CircleShape),
        //colors =CardDefaults.colors(containerColor = Color.Red, focusedContainerColor = Color. Yellow),
        scale = CardDefaults.scale(focusedScale = 1.05f) )  {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier= Modifier
                .size(40.dp),

            contentScale = ContentScale.Fit
        )

    }
}