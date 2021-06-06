package com.benio.urlimagespan

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.style.DynamicDrawableSpan
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Created by beniozhang on 2021/6/6.
 */
class URLImageSpan {
    open class Builder(private val provider: DrawableProvider = GlideDrawableProvider()) {
        private var url: String? = null
        private var placeholderDrawable: Drawable? = null
        private var placeholderId = 0
        private var useInstinctPlaceholderSize = true
        private var errorPlaceholder: Drawable? = null
        private var errorId = 0
        private var useInstinctErrorPlaceholderSize = true
        private var verticalAlignment = DynamicDrawableSpan.ALIGN_BOTTOM
        private var desiredWidth = -1
        private var desiredHeight = -1

        fun override(width: Int, height: Int): Builder {
            this.desiredWidth = width
            this.desiredHeight = height
            return this
        }

        fun url(url: String?): Builder {
            this.url = url
            return this
        }

        fun verticalAlignment(verticalAlignment: Int): Builder {
            this.verticalAlignment = verticalAlignment
            return this
        }

        fun placeholder(drawable: Drawable?): Builder {
            this.placeholderDrawable = drawable
            this.placeholderId = 0
            this.useInstinctPlaceholderSize = true
            return this
        }

        fun placeholder(drawable: Drawable?, width: Int, height: Int): Builder {
            drawable?.setBounds(0, 0, width, height)
            this.placeholderDrawable = drawable
            this.placeholderId = 0
            this.useInstinctPlaceholderSize = false
            return this
        }

        fun placeholder(@DrawableRes resourceId: Int): Builder {
            this.placeholderDrawable = null
            this.placeholderId = resourceId
            this.useInstinctPlaceholderSize = true
            return this
        }

        fun error(drawable: Drawable?): Builder {
            this.errorPlaceholder = drawable
            this.errorId = 0
            this.useInstinctErrorPlaceholderSize = true
            return this
        }

        fun error(drawable: Drawable?, width: Int, height: Int): Builder {
            drawable?.setBounds(0, 0, width, height)
            this.errorPlaceholder = drawable
            this.errorId = 0
            this.useInstinctErrorPlaceholderSize = false
            return this
        }

        fun error(@DrawableRes resourceId: Int): Builder {
            this.errorPlaceholder = null
            this.errorId = resourceId
            this.useInstinctErrorPlaceholderSize = true
            return this
        }

        fun getPlaceholderDrawable(context: Context): Drawable? {
            var drawable = placeholderDrawable
            if (drawable == null && placeholderId > 0) {
                drawable = ContextCompat.getDrawable(context, placeholderId)
            }
            if (useInstinctPlaceholderSize && drawable != null) {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            }
            return drawable
        }

        fun getErrorDrawable(context: Context): Drawable? {
            var drawable = errorPlaceholder
            if (drawable == null && errorId > 0) {
                drawable = ContextCompat.getDrawable(context, errorId)
            }
            if (useInstinctErrorPlaceholderSize && drawable != null) {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            }
            return drawable
        }

        fun buildRequest(textView: TextView): URLImageSpanRequest {
            val context = textView.context
            return URLImageSpanRequest(
                textView = textView,
                url = url,
                placeholderDrawable = getPlaceholderDrawable(context),
                errorPlaceholder = getErrorDrawable(context),
                verticalAlignment = verticalAlignment,
                desiredWidth = desiredWidth,
                desiredHeight = desiredHeight
            )
        }

        fun build(textView: TextView): DynamicDrawableSpan {
            val request = buildRequest(textView)
            return object : DynamicDrawableSpan() {
                override fun getDrawable(): Drawable {
                    request.span = this
                    return provider.get(request)
                }
            }
        }
    }
}