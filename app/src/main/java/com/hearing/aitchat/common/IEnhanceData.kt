package com.hearing.aitchat.common

/**
 * @author liujiadong
 * @since 2020/10/20
 */
interface IEnhanceData {
    fun label(): String

    fun charSequence(): CharSequence

    fun color(): String

    fun formatData(): CharSequence
}