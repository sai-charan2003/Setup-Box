package com.charan.setupBox.data.remote.responsedto

import kotlinx.serialization.Serializable

@Serializable
data class TVAuthentication(
    val id : Int? = null,
    val tv_code : String? = null,
    val email : String? = null,
    val created_at : String? = null,
    val isAuthenticated : Boolean? = null
)
