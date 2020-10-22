package com.hearing.aitchat.common

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class FormatRange(from: Int, to: Int) : Range(from, to) {
    var formatCharSequence: CharSequence? = null
}