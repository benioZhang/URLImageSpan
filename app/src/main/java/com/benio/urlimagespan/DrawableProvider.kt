package com.benio.urlimagespan

import android.graphics.drawable.Drawable

/**
 * Created by beniozhang on 2021/5/4.
 */
interface DrawableProvider {
    fun get(request: URLImageSpanRequest): Drawable
}