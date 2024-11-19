package com.charan.setupBox.data.remote.responsedto

import kotlinx.serialization.Serializable

@Serializable
data class TVAuthentication(
    val id : Int? = null,
    val tv_code : String? = null,
    val session_id : String? = null
)
