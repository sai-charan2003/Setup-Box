package com.charan.setupBox.Navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.charan.setupBox.Screens.AddNewChannel
import com.charan.setupBox.Screens.HomeScreen

@Composable
fun NavigationAppHost(navHostController: NavHostController,sharedURL : String?) {
    NavHost(
        navController = navHostController,

        startDestination = HomeScreenNav(channelLink = sharedURL),
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

    }
}