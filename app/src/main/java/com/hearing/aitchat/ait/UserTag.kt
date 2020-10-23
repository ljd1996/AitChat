package com.hearing.aitchat.ait

import com.hearing.aitchat.common.ITag

/**
 * @author liujiadong
 * @since 2020/10/22
 */
data class UserTag(private val id: String?, private val name: String?, val color: String?) : ITag {
    override fun color() = color ?: User.DEFAULT_COLOR
}