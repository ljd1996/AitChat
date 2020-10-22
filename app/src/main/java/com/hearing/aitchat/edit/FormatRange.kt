package com.hearing.aitchat.edit

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class FormatRange(from: Int, to: Int) : Range(from, to) {
    var convert: FormatData? = null
    var rangeCharSequence: CharSequence? = null

    interface FormatData {
        fun formatCharSequence(): CharSequence
    }
}