package com.example.cleanapp.extensions

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.TranslateAnimation

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

fun View.slideUp(duration:Long,height:Float = this.height.toFloat() * 3/4) {
    TranslateAnimation(
        0f,
        0f,
        0f,
        height
    ).apply {
        this.duration = duration
        fillAfter = true
        start()
    }
}