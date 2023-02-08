package com.moemaair.lictionary.core.util

import android.content.Context
import android.content.Intent

fun Context.shareApp(link:String){
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, link)
    startActivity(Intent.createChooser(intent, "Share via"))
}