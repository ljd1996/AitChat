package com.hearing.aitchat.edit

import android.content.Context
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText
import java.util.*

/**
 * @author liujiadong
 * @since 2020/10/19
 */
class AitEditView(context: Context, attrs: AttributeSet?, defStyle: Int) : AppCompatEditText(context, attrs, defStyle) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)

    companion object {
        const val TAG = "AitEditView"
    }

    private var action: Runnable? = null
    var rangeManager: RangeManager? = null

    init {
        rangeManager = RangeManager()
        addTextChangedListener(AitTextWatcher(this))
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return AitInputConnection(super.onCreateInputConnection(outAttrs), true, this)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        action = action ?: Runnable { setSelection(getText()?.length ?: 0) }
        post(action)
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

    fun insert(insertData: IInsertData) {
        val charSequence = insertData.charSequence()
        val editable = text
        var start = selectionStart
        editable?.delete(start - 1, start)
        start -= 1
        val end = start + charSequence.length
        editable?.insert(start, charSequence)
        editable?.setSpan(ForegroundColorSpan(insertData.color()), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val range = FormatRange(start, end)
        range.convert = insertData.formatData()
        range.rangeCharSequence = charSequence
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
                val newChar = range.convert?.formatCharSequence()
                builder.append(text?.substring(lastRangeTo, range.from))
                builder.append(newChar)
                lastRangeTo = range.to
            }
        }
        builder.append(text?.substring(lastRangeTo))
        return builder.toString()
    }
}