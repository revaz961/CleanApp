package com.example.cleanapp.extensions

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import com.example.cleanapp.R

fun TextView.setResourceHtmlText(id: Int, vararg args: Any?) {
    val txt = context.getString(id,*args)
    text = HtmlCompat.fromHtml(txt, FROM_HTML_MODE_LEGACY)
}

fun TextView.setTextById(id: Int, vararg args: Any?) {
    text = context.getString(id,*args)
}

fun TextView.setTextPluralsById(id: Int, count: Int) {
    text = context.resources.getQuantityString(id, count, count)
}