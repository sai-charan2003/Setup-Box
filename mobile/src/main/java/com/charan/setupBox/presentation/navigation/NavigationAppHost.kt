package com.charan.setupBox.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.charan.setupBox.presentation.addChannel.AddNewChannel
import com.charan.setupBox.presentation.home.HomeScreen
import com.charan.setupBox.presentation.login.LoginScreen
import com.charan.setupBox.presentation.settings.SettingsScreen
import com.charan.setupBox.presentation.settings.aboutapp.AboutAppScreen
import com.charan.setupBox.presentation.settings.aboutapp.LicenseScreen
import com.charan.setupBox.presentation.settings.account.AccountScreen
import com.charan.setupBox.presentation.tvAutentication.TVAuthentication

@Composable
fun NavigationAppHost(navHostController: NavHostController,sharedURL : String?,isLoggedIn : Boolean?) {
    NavHost(
        navController = navHostController,

        startDestination =if(isLoggedIn==false) LoginScreenNav else HomeScreenNav(sharedURL),
        enterTransition = {
            fadeIn() + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                initialOffset = { 100 },
                animationSpec = (tween(easing = LinearEasing, durationMillis = 200))
            )
        },
        exitTransition = {
            fadeOut() + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                targetOffset = { -100 },
                animationSpec = (tween(easing = LinearEasing, durationMillis = 200))
            )
        },
        popEnterTransition = {
            fadeIn() + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                initialOffset = { -100 },
                animationSpec = (tween(easing = LinearEasing, durationMillis = 200))
            )
        },
        popExitTransition = {
            fadeOut() + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                targetOffset = { 100 },
                animationSpec = (tween(easing = LinearEasing, durationMillis = 200))
            )
        },
    ){
        composable<HomeScreenNav> {
            val args = it.toRoute<HomeScreenNav>()
            HomeScreen(navHostController = navHostController, sharedURL = args.channelLink)
        }
        composable<AddNewChannelScreenNav> {
            val args = it.toRoute<AddNewChannelScreenNav>()
            AddNewChannel(navHostController = navHostController, id = args.id, sharedURL = args.channelLink)
        }
        composable<LoginScreenNav> {
            LoginScreen(navHostController = navHostController)
        }
        
        composable<TVAuthenticationNav> { 
            TVAuthentication(navHostController = navHostController)
        }
        composable<AccountScreenNav>(){
            AccountScreen(navHostController = navHostController)
        }
        composable<AboutAppNav> { 
            AboutAppScreen(navHostController = navHostController)
        }
        composable<SettingsScreenNav> {
            SettingsScreen(navHostController = navHostController)
        }
        composable<LicenseScreenNav> {
            LicenseScreen(navHostController = navHostController)
        }

    }
}