package com.hearing.aitchat.edit

import androidx.annotation.ColorInt

/**
 * @author liujiadong
 * @since 2020/10/20
 */
interface IInsertData {
    fun charSequence(): CharSequence

    fun formatData(): FormatRange.FormatData

    @ColorInt
    fun color(): Int
}