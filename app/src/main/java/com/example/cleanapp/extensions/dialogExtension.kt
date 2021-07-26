package com.example.cleanapp.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager

fun Dialog.init(
    view: View,
    height:Int = WindowManager.LayoutParams.WRAP_CONTENT,
    width:Int = WindowManager.LayoutParams.WRAP_CONTENT
) {
    this.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    this.setContentView(view)
    this.window!!.attributes.width = width
    this.window!!.attributes.height = height
}