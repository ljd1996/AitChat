package com.hearing.aitchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.hearing.aitchat.edit.AitEditView
import com.hearing.aitchat.edit.Range
import com.hearing.aitchat.text.AitTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aitEditView = findViewById<AitEditView>(R.id.send_et)
        val aitTextView = findViewById<AitTextView>(R.id.chat_tv)
        val originView = findViewById<TextView>(R.id.origin_tv)
        findViewById<TextView>(R.id.click_tv)?.setOnClickListener {
            Log.d("LLL", "s = ${aitEditView.getFormatCharSequence()}")
            aitTextView.setHtml(aitEditView.getFormatCharSequence())
            originView.text = aitEditView.getFormatCharSequence()
        }
    }
}