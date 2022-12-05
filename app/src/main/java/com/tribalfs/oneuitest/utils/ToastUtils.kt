package com.tribalfs.oneuitest.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import dev.oneuiproject.oneui.widget.Toast


fun Context.showToast(@StringRes msg: Int,  duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, msg, duration).show()
}

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, duration).show()
}

fun Context.showToast(msg: String, drawable: Drawable,duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, msg, duration).show()
}

