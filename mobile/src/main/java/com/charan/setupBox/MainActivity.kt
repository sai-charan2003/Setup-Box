package com.charan.setupBox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.charan.setupBox.navigation.NavigationAppHost
import com.charan.setupBox.ui.theme.SetupBoxTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        var sharedURL : String? = null
        if (Intent.ACTION_SEND == intent.action && intent.type != null) {

            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)

            if (sharedText != null) {
                sharedURL = sharedText

            }
        }
        setContent {

            SetupBoxTheme(){
                WindowCompat.setDecorFitsSystemWindows(window, false)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {

                    val navController = rememberNavController()
                    Log.d("TAG", "onCreate: $sharedURL")

                    NavigationAppHost(navHostController = navController, sharedURL = sharedURL)

                }



            }
        }

    }
}