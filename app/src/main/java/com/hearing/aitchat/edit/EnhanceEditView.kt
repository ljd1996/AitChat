package com.hearing.aitchat.edit

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.widget.AppCompatEditText
import com.hearing.aitchat.R
import com.hearing.aitchat.common.FormatRange
import com.hearing.aitchat.common.IEnhanceData
import com.hearing.aitchat.common.RangeManager

/**
 * @author liujiadong
 * @since 2020/10/19
 */
class EnhanceEditView(context: Context, attrs: AttributeSet?, defStyle: Int) : AppCompatEditText(context, attrs, defStyle) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)

    var rangeManager: RangeManager? = null

    init {
        rangeManager = RangeManager()
        addTextChangedListener(EnhanceTextWatcher(this))
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return EnhanceInputConnection(super.onCreateInputConnection(outAttrs), true, this)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        rangeManager?.getRangeOfNearby(selStart, selEnd)?.let { nearbyRange ->
            if (selStart == selEnd) {
                setSelection(nearbyRange.getAnchorPosition(selStart))
            } else {
                if (selEnd < nearbyRange.to) {
                    setSelection(selStart, nearbyRange.to)
                }
                if (selStart > nearbyRange.from) {
                    setSelection(nearbyRange.from, selEnd)
                }
            }
        }
    }

    fun insert(enhanceData: IEnhanceData, del: Int) {
        val charSequence = enhanceData.charSequence()
        val editable = text
        var start = selectionStart
        if (start < del) return
        editable?.delete(start - del, start)
        start -= del
        val end = start + charSequence.length
        editable?.insert(start, charSequence)
        editable?.setSpan(
            ForegroundColorSpan(Color.parseColor(enhanceData.color())),
            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val range = FormatRange(start, end)
        range.formatCharSequence = enhanceData.formatData()
        rangeManager?.add(range)
    }

    fun getFormatCharSequence(): CharSequence {
        if (rangeManager?.isEmpty() == true) {
            return text ?: ""
        }
        var lastRangeTo = 0
        val builder = StringBuilder("")
        rangeManager?.getSorted()?.forEach { range ->
            if (range is FormatRange) {
                val newChar = range.formatCharSequence
                builder.append(text?.substring(lastRangeTo, range.from))
                builder.append(newChar)
                lastRangeTo = range.to
            }
        }
        builder.append(text?.substring(lastRangeTo))
        return builder.toString()
    }
}