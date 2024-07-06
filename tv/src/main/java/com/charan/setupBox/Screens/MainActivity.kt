package com.charan.setupBox.Screens

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.charan.setupBox.ViewModel.ViewModel
import com.charan.setupBox.ui.theme.SetupBoxTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SetupBoxTheme(isInDarkTheme = true) {
                val viewModel = viewModel<ViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return ViewModel(
                                application
                            ) as T
                        }
                    }

                )
                viewModel.getSupabaseData()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape,

                ) {
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val application = LocalContext.current.applicationContext as Application


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SetupBoxTheme {
        Greeting("Android")
    }
}

fun openYoutubeLink(youtubeID: String,context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@ThePermitRoomMedia"))
    intent.setPackage("com.google.android.youtube.tv")
    startActivity(context,intent,null)

//    val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "https://www.youtube.com/@ThePermitRoomMedia"))
//    val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@ThePermitRoomMedia"))
//    try {
//        context.startActivity(intentApp)
//    } catch (ex: ActivityNotFoundException) {
//        context.startActivity(intentBrowser)
//    }

}