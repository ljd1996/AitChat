package com.hearing.aitchat.edit

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import com.hearing.aitchat.ait.User
import com.hearing.aitchat.common.IEnhanceData
import com.hearing.aitchat.selector.DataAdapter
import com.hearing.aitchat.selector.SelectorPopupView
import com.hearing.aitchat.topic.Topic

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class EnhanceTextWatcher(private val editView: EnhanceEditView) : TextWatcher {
    private val rangeManager = editView.rangeManager
    private val selectorPopupView = SelectorPopupView(editView.context)
    private var delNeed = 0

    init {
        selectorPopupView.setDataChooseListener(object : DataAdapter.OnDataChooseListener {
            override fun select(data: IEnhanceData) {
                editView.insert(data, delNeed)
                selectorPopupView.dismiss()
            }
        })
    }

    override fun afterTextChanged(s: Editable?) {
    }

    // 若从中间插入字符，需要将插入位置后面的Range相应挪位
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        editView.text?.let { editable ->
            if (start < editable.length) {
                val end = start + count
                val offset = after - count

                if (start != end && rangeManager?.isEmpty() == false) {
                    editable.getSpans(start, end, ForegroundColorSpan::class.java)?.forEach { span ->
                        editable.removeSpan(span)
                    }
                }

                val iterator = rangeManager?.iterator()
                while (iterator?.hasNext() == true) {
                    val range = iterator.next()
                    if (range.isWrapped(start, end)) {
                        iterator.remove()
                        continue
                    }
                    if (range.from >= end) {
                        range.setOffset(offset)
                    }
                }
            }
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s == null) {
            return
        }
        handleAit(s, start, count)
        handleTopic(s, start, count)
    }

    private fun handleAit(s: CharSequence, start: Int, count: Int) {
        if (count == 1 && !TextUtils.isEmpty(s)) {
            val inputChar = s.toString()[start].toString()
            if (TextUtils.equals(User.AIT, inputChar)) {
                delNeed = 1
                selectorPopupView.show(editView, SelectorPopupView.SELECTOR_TYPE_USER)
            }
        }
        val index = s.lastIndexOf(User.AIT)
        if (index >= 0 && index < s.length - 1 && rangeManager?.contains(index) == false) {
            delNeed = s.length - index
            selectorPopupView.show(editView, SelectorPopupView.SELECTOR_TYPE_USER, s.substring(index + 1, s.length))
        }
    }

    private fun handleTopic(s: CharSequence, start: Int, count: Int) {
        if (count == 1 && !TextUtils.isEmpty(s)) {
            val inputChar = s.toString()[start].toString()
            if (TextUtils.equals(Topic.TOPIC, inputChar)) {
                delNeed = 1
                selectorPopupView.show(editView, SelectorPopupView.SELECTOR_TYPE_TOPIC)
            }
        }
        val index = s.lastIndexOf(Topic.TOPIC)
        if (index >= 0 && index < s.length - 1 && rangeManager?.contains(index) == false) {
            delNeed = s.length - index
            selectorPopupView.show(editView, SelectorPopupView.SELECTOR_TYPE_TOPIC, s.substring(index + 1, s.length))
        }
    }
}