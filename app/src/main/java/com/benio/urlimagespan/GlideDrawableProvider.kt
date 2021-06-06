package com.benio.urlimagespan

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.style.ImageSpan
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by beniozhang on 2021/5/4.
 */
class GlideDrawableProvider : DrawableProvider {
    override fun get(request: URLImageSpanRequest): Drawable {
        val drawable = if (request.url.isNullOrEmpty()) {
            request.placeholderDrawable ?: request.errorPlaceholder
        } else {
            execute(request)
            request.placeholderDrawable
        }
        return drawable ?: ColorDrawable()/*Can't be null*/
    }

    fun execute(request: URLImageSpanRequest) {
        val view = request.view ?: return
        val span = request.span
        Glide.with(view)
            .load(request.url)
            .error(request.errorPlaceholder)
            .override(request.desiredWidth, request.desiredHeight)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    resource.setBounds(0, 0, resource.intrinsicWidth, resource.intrinsicHeight)
                    onResponse(request, resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    if (errorDrawable != null) {
                        onResponse(request, errorDrawable)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                private fun onResponse(request: URLImageSpanRequest, drawable: Drawable) {
                    val spannable = request.view?.text as? Spannable ?: return
                    spannable.replaceSpan(
                        span,
                        ImageSpan(drawable, request.verticalAlignment),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            })
    }

    fun Spannable.replaceSpan(oldSpan: Any?, newSpan: Any?, flags: Int): Boolean {
        if (oldSpan == null || newSpan == null) {
            return false
        }
        val start = getSpanStart(oldSpan)
        val end = getSpanEnd(oldSpan)
        if (start == -1 || end == -1) {
            return false
        }
        removeSpan(oldSpan)
        setSpan(newSpan, start, end, flags)
        return true
    }
}


