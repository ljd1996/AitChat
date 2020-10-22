package com.hearing.aitchat.edit

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import com.hearing.aitchat.userlist.UserAdapter
import com.hearing.aitchat.userlist.UserPopupView

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class AitTextWatcher(private val editView: AitEditView) : TextWatcher {
    private val rangeManager = editView.rangeManager
    private val userPopupView = UserPopupView(editView.context)

    init {
        userPopupView.setUserChooseListener(object : UserAdapter.OnUserChooseListener {
            override fun select(user: User) {
                editView.insert(user)
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
        if (count == 1 && !TextUtils.isEmpty(s)) {
            val mentionChar = s?.toString()?.get(start)
            if (mentionChar == '@') {
                userPopupView.show(editView, null)
            }
        }
    }
}