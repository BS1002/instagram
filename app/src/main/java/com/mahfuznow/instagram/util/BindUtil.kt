package com.mahfuznow.instagram.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahfuznow.instagram.R

// this can't be a class because BindingAdapter methods have to be static

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .placeholder(R.color.grey_trans_50)
        .into(imageView)
}

@BindingAdapter("loadCircularImage")
fun loadCircularImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.color.grey_trans_50)
        .into(imageView)
}
