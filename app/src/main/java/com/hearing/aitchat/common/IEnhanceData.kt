package com.hearing.aitchat.common

/**
 * @author liujiadong
 * @since 2020/10/20
 */
interface IEnhanceData {
    fun charSequence(): CharSequence

    fun color(): String

    fun formatData(): CharSequence
}