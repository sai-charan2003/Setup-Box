package com.charan.setupBox.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.PivotOffsets
import androidx.tv.foundation.lazy.list.TvLazyListScope
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.charan.setupBox.data.local.entity.SetupBoxContent

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun NewsChannelsView(newsItem:List<SetupBoxContent>) {
    rememberTvLazyListState()
    PivotOffsets()
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        items(newsItem.size) { item ->
            StandardCardContainer(
                imageCard = { interactionSource ->
                    AsyncImage(
                        model = newsItem[item].channelPhoto,
                        modifier = Modifier.size(250.dp),
                        contentDescription = null
                    )
                }, title = { Text(text = newsItem[item].channelName.toString()) })

        }
    }

}