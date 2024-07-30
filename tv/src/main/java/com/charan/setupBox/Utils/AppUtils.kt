package com.charan.setupBox.Utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat

object AppUtils {

    fun openLink(context: Context,appPackage: String,link:String){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            intent.setPackage(appPackage)
            ContextCompat.startActivity(context, intent, null)
        } catch (e:Exception){
            openApp(context,appPackage)
        }
    }

    private fun openApp(context: Context, packageName: String) {
        try {
            val packageManager: PackageManager = context.packageManager

            val launchIntent: Intent? = packageManager.getLaunchIntentForPackage(packageName)

            if (launchIntent != null) {

                context.startActivity(launchIntent)
            } else {
                val playStoreIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                if (playStoreIntent.resolveActivity(packageManager) != null) {
                    context.startActivity(playStoreIntent)
                }
            }
        } catch (e: Exception){
            Toast.makeText(context, e.message,Toast.LENGTH_LONG).show()
        }
    }


}