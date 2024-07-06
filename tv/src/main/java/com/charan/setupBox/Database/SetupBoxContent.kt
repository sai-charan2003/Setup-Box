package com.charan.setupBox.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "setupBoxContent")
@Serializable
data class SetupBoxContent (
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val channelLink:String?=null,
    val channelName:String?=null,
    val channelPhoto:String?=null,
    val Category:String?=null,
    val app_Package:String?=null

)