package com.hearing.aitchat.text

import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author liujiadong
 * @since 2020/10/21
 */
class EnhanceTextView(context: Context, attrs: AttributeSet?, defStyle: Int) : AppCompatTextView(context, attrs, defStyle) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    fun setEnhanceText(text: CharSequence?) {
        try {
            val spaceTxt = text?.toString()?.replace("\n", "<br>")
                ?.replace("  ", "&nbsp;&nbsp;")
            super.setText(parse(spaceTxt))
            stripUnderlines()
            movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parse(source: CharSequence?): Spanned {
        if (TextUtils.isEmpty(source)) {
            return SpannableString("")
        }
        return Html.fromHtml(source?.toString() ?: "", null, EnhanceTagHandler())
    }

    private fun stripUnderlines() {
        text?.let {
            val s = SpannableString(it)
            val spans = s.getSpans(0, s.length, URLSpan::class.java)
            for (span in spans) {
                val start = s.getSpanStart(span)
                val end = s.getSpanEnd(span)
                val newSpan = URLSpanNoUnderline(span.url)
                s.removeSpan(span)
                s.setSpan(newSpan, start, end, 0)
            }
            this.text = s
        }
    }

    internal class URLSpanNoUnderline(url: String?) : URLSpan(url) {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = Color.parseColor("#F51F7B")
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {

        }
    }
}