package com.charan.setupBox.utils

data class AddChannelState(
    val channelName: String = "",
    val channelLink: String = "",
    val packageName: String = "",
    val channelPhoto: String = "",
    val category: String = "",
    val uuid: String = "",
    val showPreviewAlertBox: Boolean = false,
    val showDropdown: Boolean = false,
    val distinctAppPackages: List<String?> = emptyList(),
    val title : String = "Add Channel",
    val errors : String? = null

)