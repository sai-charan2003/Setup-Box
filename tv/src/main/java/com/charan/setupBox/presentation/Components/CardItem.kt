package com.charan.setupBox.presentation.Components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CardLayoutDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.StandardCardLayout
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.charan.setupBox.utils.AppUtils
import com.charan.setupBox.data.local.entity.SetupBoxContent

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CardItem(item: SetupBoxContent, context:Context, modifier: Modifier){

    StandardCardLayout(
        modifier = Modifier
            .width(300.dp)
            .padding(25.dp)
            .then(modifier),
        imageCard = {
            CardLayoutDefaults.ImageCard(
                onClick = {
                    AppUtils.openLink(context, item.app_Package!!, item.channelLink!!)
                },
                interactionSource = it,
                colors = CardDefaults.colors(containerColor = Color.Transparent),
                border = CardDefaults.border(focusedBorder = Border(
                    BorderStroke(2.dp,Color.White),
                    inset = 4.dp
                )),


            ) {
                AsyncImage(
                    model = item.channelPhoto,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(CardDefaults.HorizontalImageAspectRatio),
                    contentScale = ContentScale.Crop,

                    contentDescription = null
                )
            }

        },
        title = {
            Text(
                text = item.channelName.toString(),
                modifier = Modifier.padding(top = 14.dp)
            )
        },



        )
}