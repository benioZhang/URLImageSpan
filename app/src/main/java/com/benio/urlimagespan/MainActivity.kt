package com.benio.urlimagespan

import android.content.res.Resources
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        findViewById<View>(R.id.btn_start).setOnClickListener {
            loadUrlSpan(textView)
        }
    }

    private fun loadUrlSpan(textView: TextView) {
        val ss =
            SpannableString("<img>To be or not to be, that is the question（生存还是毁灭，这是一个值得考虑的问题）")
        val urlImageSpan = URLImageSpan.Builder()
            .url("https://sf6-ttcdn-tos.pstatp.com/img/user-avatar/d8111dfb52a63f3f12739194cf367754~500x500.png")
            .override(100.dp, 100.dp)
            .build(textView)
        ss.setSpan(urlImageSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(ss, TextView.BufferType.SPANNABLE) // 必需设置
    }

    val Int.dp: Int
        get() = run {
            val metrics = Resources.getSystem().displayMetrics
            return (this * metrics.density).roundToInt()
        }
}