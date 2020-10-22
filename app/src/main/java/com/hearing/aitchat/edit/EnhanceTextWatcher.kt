package com.hearing.aitchat.edit

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import com.hearing.aitchat.ait.User
import com.hearing.aitchat.ait.UserAdapter
import com.hearing.aitchat.ait.UserPopupView

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class EnhanceTextWatcher(private val editView: EnhanceEditView) : TextWatcher {
    private val rangeManager = editView.rangeManager
    private val userPopupView = UserPopupView(editView.context)
    private var delNeed = 0

    init {
        userPopupView.setUserChooseListener(object : UserAdapter.OnUserChooseListener {
            override fun select(user: User) {
                editView.insert(user, delNeed)
                userPopupView.dismiss()
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
        if (count == 1 && !TextUtils.isEmpty(s)) {
            val mentionChar = s.toString()[start].toString()
            if (TextUtils.equals(User.AIT, mentionChar)) {
                delNeed = 1
                userPopupView.show(editView)
            }
        }
        val index = s.lastIndexOf(User.AIT)
        if (index >= 0 && index < s.length - 1) {
            delNeed = s.length - index
            userPopupView.show(editView, s.substring(index + 1, s.length))
        }
    }
}