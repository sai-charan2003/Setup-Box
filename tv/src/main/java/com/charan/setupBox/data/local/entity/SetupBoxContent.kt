package com.charan.setupBox.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "setupBoxContent",
    indices = [Index(value = ["uuid"], unique = true)])
@Serializable
data class SetupBoxContent (
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val channelLink:String?=null,
    val channelName:String?=null,
    val channelPhoto:String?=null,
    val Category:String?=null,
    val app_Package:String?=null,
    val uuid : String? = null,
    val email : String? = null

)