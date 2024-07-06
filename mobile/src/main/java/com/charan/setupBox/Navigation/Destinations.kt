package com.charan.setupBox.Navigation

sealed class Destinations(val route:String) {
    object Home: Destinations("Home")
    object AddNewChannel: Destinations("AddNewChannel/{ID}")

}