package com.example.cleanapp.extensions

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cleanapp.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun ImageView.load(
    url: String?,
    placeholder: Int = R.drawable.image_not_found,
    error: Int = R.drawable.image_not_found
) {
    Glide.with(this.context)
        .load(url ?: "")
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

fun ImageView.loadFromStorage(
    path: String,
    placeholder: Int = R.drawable.image_not_found,
    error: Int = R.drawable.image_not_found
) {
    val storageReference = Firebase.storage.reference.child("images/$path/profile")
    com.example.cleanapp.utils.GlideApp.with(context).load(storageReference)
        .placeholder(placeholder)
        .error(error)
        .into(this)
}

fun ImageView.setImageTintColor(hexColor: String) {
    val color = Color.parseColor(hexColor)
    imageTintList = ColorStateList.valueOf(color)
}
