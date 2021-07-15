package com.example.cleanapp.extensions

import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager

fun Dialog.init(
    view: View
) {
    this.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    this.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    this.setContentView(view)
    this.window!!.attributes.width = WindowManager.LayoutParams.WRAP_CONTENT
    this.window!!.attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
}