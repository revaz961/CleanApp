package com.example.cleanapp.extensions

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.cleanapp.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.goneIf(isValid: Boolean) {
    if (isValid)
        gone()
    else
        show()
}

fun View.showIf(isValid: Boolean) {
    if (isValid)
        show()
    else
        hide()
}

fun View.expand() {
    if (height != 0)
        return

    measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )

    val targetHeight = measuredHeight

    ValueAnimator.ofFloat(0f, targetHeight.toFloat()).apply {
        addUpdateListener {
            val params = layoutParams
            params.height = (it.animatedValue as Float).toInt()
            layoutParams = params
        }
        duration = 500
        start()
    }
}

fun View.collapse() {
    ValueAnimator.ofFloat(height.toFloat(), 0f).apply {
        addUpdateListener {
            val params = layoutParams
            params.height = (it.animatedValue as Float).toInt()
            layoutParams = params
        }
        duration = 500
        start()
    }
}

fun View.collapseIf(isValid: Boolean = height != 0) {
    if (isValid)
        collapse()
    else
        expand()
}

fun View.slide(multiplier: Float = 3 / 5f, duration: Long = 300) {
    val translate = -height * multiplier
    animate().duration = duration
    if (translationY == 0f)
        animate().translationY(translate)
    else
        animate().translationY(0f)
}

fun View.setBorder(width: Int = 1, color: String = "#FFFFFF", radius: Float = 20f) {
    val shape = GradientDrawable()
    shape.setStroke(width, Color.parseColor(color))
    shape.cornerRadius = radius
    shape.setColor(Color.parseColor("#99FFFFFF"))
    background = shape
}

fun View.setTintColor(hexColor:String){
    val color = Color.parseColor(hexColor)
    backgroundTintList = ColorStateList.valueOf(color)
}