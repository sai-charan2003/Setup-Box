package com.charan.setupBox.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class HomeScreenNav(
    val channelLink: String?
)

@Serializable
data class AddNewChannelScreenNav(
    val id : Int?,
    val channelLink : String?
)

@Serializable
object LoginScreenNav

@Serializable
object TVAuthenticationNav
