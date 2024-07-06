package com.charan.setupBox.Utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat

object AppUtils {

    fun openLink(context: Context,appPackage: String,link:String){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            intent.setPackage(appPackage)
            ContextCompat.startActivity(context, intent, null)
        } catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }
    }
}