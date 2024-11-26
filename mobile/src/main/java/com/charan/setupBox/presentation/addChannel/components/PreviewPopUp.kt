package com.charan.setupBox.presentation.addChannel.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PreviewAlertBox(imageLink:String,onClick:(Boolean)->Unit){
    AlertDialog(onDismissRequest = {
        onClick(false)

    }, confirmButton = {
        TextButton(onClick = { onClick(true) }) {
            Text("Done")

        }

    },

        title = {
            Text("Preview")
        },
        text = {
            AsyncImage(model = imageLink, contentDescription = null, modifier = Modifier.fillMaxWidth().requiredHeight(100.dp).requiredWidth(300.dp))
        }

    )

}