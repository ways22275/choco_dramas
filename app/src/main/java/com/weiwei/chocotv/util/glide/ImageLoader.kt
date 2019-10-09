package com.weiwei.chocotv.util.glide

import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    fun imageLoader(imageView : ImageView, url : String?) {
        if (!url.isNullOrEmpty()) {
            GlideApp.with(imageView.context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(imageView)
        }
    }
}