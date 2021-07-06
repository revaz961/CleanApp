package com.example.cleanapp.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cleanapp.R

fun ImageView.load(
    url: String?,
    placeholder: Int = R.drawable.image_not_found,
    error: Int = R.drawable.image_not_found
) {
    Glide.with(this.context)
        .load(url ?: "")
        .placeholder(placeholder)
        .error(error)
        .into(this);
}