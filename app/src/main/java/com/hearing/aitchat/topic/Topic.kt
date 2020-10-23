package com.hearing.aitchat.topic

import com.hearing.aitchat.common.IEnhanceData

/**
 * @author liujiadong
 * @since 2020/10/22
 */
class Topic(val id: String, val label: String, val url: String) : IEnhanceData {
    companion object {
        const val TOPIC = "#"
        const val TOPIC_TAG = "topic"
        const val ID = "id"
        const val LABEL = "label"
        const val URL = "url"
        const val COLOR = "color"
        const val DEFAULT_COLOR = "#0000FF"
        private const val TOPIC_FORMAT = "<$TOPIC_TAG $ID='%s' $LABEL='%s' $URL='%s' $COLOR='%s'>%s</$TOPIC_TAG>"
    }

    override fun label() = label

    override fun charSequence() = " $TOPIC$label$TOPIC "

    override fun color() = DEFAULT_COLOR

    override fun formatData() = String.format(TOPIC_FORMAT, id, label, url, color(), charSequence())
}