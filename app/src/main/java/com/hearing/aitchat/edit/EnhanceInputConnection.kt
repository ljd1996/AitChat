package com.hearing.aitchat.edit

import android.view.KeyEvent
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class EnhanceInputConnection(target: InputConnection, mutable: Boolean, private val editText: EnhanceEditView) :
    InputConnectionWrapper(target, mutable) {
    private val rangeManager = editText.rangeManager

    override fun sendKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
            val selectionStart = editText.selectionStart
            val selectionEnd = editText.selectionEnd
            if (selectionStart == selectionEnd) {
                rangeManager?.getRangeOfLeft(selectionStart)?.let {
                    rangeManager.remove(it)
                    editText.text?.delete(it.from, it.to)
                    return true
                }
            }
        }
        return super.sendKeyEvent(event)
    }
}