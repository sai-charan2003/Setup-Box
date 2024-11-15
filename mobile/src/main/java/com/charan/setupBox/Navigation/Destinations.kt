package com.charan.setupBox.Navigation

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
