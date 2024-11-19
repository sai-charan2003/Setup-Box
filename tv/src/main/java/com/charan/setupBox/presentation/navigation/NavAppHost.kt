package com.charan.setupBox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.charan.setupBox.presentation.HomeScreen
import com.charan.setupBox.presentation.login.LoginScreen
import com.charan.setupBox.presentation.login.OTPScreen
import io.github.jan.supabase.gotrue.providers.builtin.OTP

@Composable
fun NavAppHost(
    navHostController: NavHostController,
    isLoggedIn : Boolean
){
    NavHost(
        navController = navHostController,
        startDestination = if(isLoggedIn)HomeScreenNav else LoginScreenNav) {
        composable<HomeScreenNav> {
            HomeScreen(navHostController)
        }
        composable<LoginScreenNav> {
            LoginScreen(navHostController)
        }
        composable<OTPScreenNav> {
            val arg = it.toRoute<OTPScreenNav>()
            OTPScreen(navHostController = navHostController, emailId = arg.email!!)
        }

    }
}