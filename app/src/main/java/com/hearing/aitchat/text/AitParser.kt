package com.hearing.aitchat.text

import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils

/**
 * @author liujiadong
 * @since 2020/10/22
 */
class AitParser : IParser {
    override fun convert(source: CharSequence?): Spanned {
        if (TextUtils.isEmpty(source)) {
            return SpannableString("")
        }
        return Html.fromHtml(source?.toString() ?: "", null, AitTagHandler())
    }
}