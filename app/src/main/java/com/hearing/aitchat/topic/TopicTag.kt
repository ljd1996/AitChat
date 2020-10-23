package com.hearing.aitchat.topic

import com.hearing.aitchat.common.ITag

/**
 * @author liujiadong
 * @since 2020/10/22
 */
data class TopicTag(val id: String?, val label: String?, val url: String?, val color: String?) : ITag {
    override fun color() = color ?: Topic.DEFAULT_COLOR
}