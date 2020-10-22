package com.hearing.aitchat.text

import android.graphics.Color
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.hearing.aitchat.utils.HtmlParserUtil
import org.xml.sax.XMLReader
import java.util.*

/**
 * @author liujiadong
 * @since 2020/10/22
 */
class AitTagHandler : Html.TagHandler {
    companion object {
        private const val USER = "user"
        private const val ID = "id"
        private const val NAME = "name"
    }

    override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {
        if (tag?.toLowerCase(Locale.ROOT).equals(USER)) {
            if (opening) {
                handleStart(output ?: return, xmlReader ?: return)
            } else {
                handleEnd(output ?: return)
            }
        }
    }

    private fun handleStart(output: Editable, xmlReader: XMLReader) {
        val map = HtmlParserUtil.parseStart(xmlReader)
        val id = map[ID]
        val name = map[NAME]
        output.setSpan(TagBean(id, name), output.length, output.length, Spannable.SPAN_MARK_MARK)
    }

    private fun handleEnd(text: Editable) {
        val len = text.length
        val objs = text.getSpans(0, text.length, TagBean::class.java)
        val obj = if (objs.isEmpty()) null else objs[objs.size - 1]
        val where = text.getSpanStart(obj)
        text.removeSpan(obj)
        if (where != len && obj != null) {
            text.setSpan(object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.color = Color.parseColor("#00FF00")
                    ds.isUnderlineText = false
                }

                override fun onClick(widget: View) {

                }
            }, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}