package com.charan.setupBox.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.charan.setupBox.Screens.Components.CardItem
import com.charan.setupBox.R
import com.charan.setupBox.Utils.Constants
import com.charan.setupBox.ViewModel.ViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ViewModel){
    val context = LocalContext.current
     val data = viewModel.allData.collectAsState()
    val newsItems = data.value.filter { it.Category == Constants.NEWS }
    val entertainment = data.value.filter { it.Category==Constants.ENTERTAINMENT}
    val focusRequester = remember { FocusRequester() }



    Column(modifier=Modifier.fillMaxSize().background(Color.Black)) {

        TvLazyColumn(modifier = Modifier) {
            item{
                Column(modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp), horizontalAlignment = Alignment.End) {
                    Row {
                        IconButton(onClick = { viewModel.getSupabaseData() },modifier=Modifier.padding(end=7.dp,top=2.dp),) {
                            Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)

                        }
                        Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = null,modifier= Modifier
                            .size(40.dp)
                            .padding(start = 5.dp, end = 5.dp))
                        Text(
                            text = Constants.AppName,
                            modifier = Modifier.padding( end = 25.dp),
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Text(Constants.NEWS, modifier = Modifier.padding(start = 20.dp, top = 50.dp))
            }

            item {

                TvLazyRow {

                    items(newsItems.size) { cardItem ->
                        if(cardItem==0){
                            LaunchedEffect(key1 = Unit) {
                                focusRequester.requestFocus()

                            }
                        }

                        CardItem(
                            item = newsItems[cardItem],
                            context = context,
                            if (cardItem == 0) Modifier.focusRequester(focusRequester) else Modifier
                        )
                    }
                }
            }
            item{
                Text(Constants.ENTERTAINMENT, modifier = Modifier.padding(start = 20.dp, top = 50.dp))

            }
            item {
                TvLazyRow {
                    items(entertainment.size) { cardItem ->


                        CardItem(
                            item = entertainment[cardItem],
                            context = context,
                            modifier=Modifier
                            )

                    }
                }
            }


        }
    }
}