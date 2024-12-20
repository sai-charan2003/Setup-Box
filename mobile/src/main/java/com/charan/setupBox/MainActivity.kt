package com.charan.setupBox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.charan.setupBox.presentation.navigation.NavigationAppHost
import com.charan.setupBox.ui.theme.SetupBoxTheme
import com.charan.setupBox.utils.SupabaseUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLoggedIn = mutableStateOf(false)
        val isExecuted = mutableStateOf(false)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !isExecuted.value
            }
            lifecycleScope.launch {
                    isLoggedIn.value = SupabaseUtils.getSessionToken(this@MainActivity)
                    Log.d("TAG", "onCreate: $isLoggedIn")
                    delay(100)
                    isExecuted.value = true
            }
        }
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
                    NavigationAppHost(navHostController = navController, sharedURL = sharedURL,isLoggedIn = isLoggedIn.value)

                }



            }
        }

    }
}