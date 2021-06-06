package com.benio.urlimagespan

import android.graphics.drawable.Drawable
import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * Created by beniozhang on 2021/5/5.
 */
class URLImageSpanRequest(
    textView: TextView,
    val url: String?,
    val placeholderDrawable: Drawable?,
    val errorPlaceholder: Drawable?,
    val desiredWidth: Int,
    val desiredHeight: Int,
    val verticalAlignment: Int
) {
    private val viewRef = WeakReference(textView)
    val view get():TextView? = viewRef.get()
    var span: Any? = null
}