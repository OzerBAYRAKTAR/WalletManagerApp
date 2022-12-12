package com.example.walletmanagerapplication.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.walletmanagerapplication.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.NonCancellable.start


fun Context.circularProgressDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 5f
        centerRadius = 80f
        start()
    }
}
fun ImageView.glideImage(url: String, isBLur: Boolean? = false) {
    if (isBLur == true) {
        Glide.with(this.context)
            .load(url)
            .override(50, 50)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(bitmapTransform(BlurTransformation(10, 1)))
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(this.context.circularProgressDrawable())
            .into(this)
    } else {
        Glide.with(this.context)
            .load(url)
            .override(500,500)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(this.context.circularProgressDrawable())
            .into(this)
    }
}
