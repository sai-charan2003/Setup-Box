package com.charan.setupBox.presentation.addChannel.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(value:String,onValueChange: (String) -> Unit,modifier: Modifier,label:String,showButton:Boolean=false,icon:ImageVector?=null,onClick: () -> Unit = {},isButtonEnabled:Boolean=false) {
        OutlinedTextField(
        value = value,
        onValueChange = {
           onValueChange(it)
        },
        label = {
            Text(text = label)
        },
        trailingIcon = {
            if(showButton){
                FilledTonalIconButton(onClick = { onClick() },modifier=Modifier.padding(5.dp), enabled = isButtonEnabled) {
                    Icon(imageVector = icon!!,null,)

                }
            }
        },

        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        )





}