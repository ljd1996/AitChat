package com.hearing.aitchat.text

import android.graphics.Color
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.hearing.aitchat.ait.UserTag
import com.hearing.aitchat.ait.User
import org.xml.sax.XMLReader
import java.util.*

/**
 * @author liujiadong
 * @since 2020/10/22
 */
class EnhanceTagHandler : Html.TagHandler {
    override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {
        if (output == null || xmlReader == null) {
            return
        }
        if (tag?.toLowerCase(Locale.ROOT).equals(User.USER)) {
            if (opening) handleAitStart(output, xmlReader) else handleAitEnd(output)
        }
    }

    private fun handleAitStart(output: Editable, xmlReader: XMLReader) {
        val map = parseElement(xmlReader)
        val id = map[User.ID]
        val name = map[User.NAME]
        val color = map[User.COLOR]
        output.setSpan(UserTag(id, name, color), output.length, output.length, Spannable.SPAN_MARK_MARK)
    }

    private fun handleAitEnd(text: Editable) {
        val len = text.length
        val objs = text.getSpans(0, text.length, UserTag::class.java)
        val obj = if (objs.isEmpty()) null else objs[objs.size - 1]
        val where = text.getSpanStart(obj)
        text.removeSpan(obj)
        if (where != len && obj != null) {
            text.setSpan(object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.color = Color.parseColor(obj.color)
                    ds.isUnderlineText = false
                }

                override fun onClick(widget: View) {

                }
            }, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun parseElement(xmlReader: XMLReader): Map<String, String> {
        val attributes = HashMap<String, String>()
        try {
            val elementField = xmlReader.javaClass.getDeclaredField("theNewElement")
            elementField.isAccessible = true
            val element = elementField[xmlReader]
            val attsField = element.javaClass.getDeclaredField("theAtts")
            attsField.isAccessible = true
            val atts = attsField[element]
            val dataField = atts.javaClass.getDeclaredField("data")
            dataField.isAccessible = true
            val data = dataField[atts] as Array<String>
            val lengthField = atts.javaClass.getDeclaredField("length")
            lengthField.isAccessible = true
            val len = lengthField[atts] as Int
            for (i in 0 until len) {
                attributes[data[i * 5 + 1]] = data[i * 5 + 4]
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return attributes
    }
}