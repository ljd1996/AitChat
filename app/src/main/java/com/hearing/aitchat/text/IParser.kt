package com.hearing.aitchat.text

import android.text.Spanned

/**
 * @author liujiadong
 * @since 2020/10/22
 */
interface IParser {
    fun convert(source: CharSequence?): Spanned
}