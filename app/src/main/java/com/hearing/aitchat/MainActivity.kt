package com.hearing.aitchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.hearing.aitchat.edit.EnhanceEditView
import com.hearing.aitchat.text.EnhanceTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aitEditView = findViewById<EnhanceEditView>(R.id.send_et)
        val aitTextView = findViewById<EnhanceTextView>(R.id.chat_tv)
        val originView = findViewById<TextView>(R.id.origin_tv)
        findViewById<TextView>(R.id.click_tv)?.setOnClickListener {
            aitTextView.setEnhanceText(aitEditView.getFormatCharSequence())
            originView.text = aitEditView.getFormatCharSequence()
        }
    }
}