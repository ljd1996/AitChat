package com.hearing.aitchat.selector

import com.hearing.aitchat.topic.Topic

/**
 * @author liujiadong
 * @since 2020/10/21
 */
object TopicFactory {
    private val topics = mutableListOf<Topic>()

    init {
        for (i in 0..20) {
            topics.add(Topic("$i", "topic-$i", "https://www.baidu.com/s?wd=$i"))
        }
    }

    fun getTopicsByKey(key: String?): List<Topic> {
        if (key == null) {
            return topics
        }
        val res = mutableListOf<Topic>()
        topics.forEach {
            if (it.label.contains(key)) {
                res.add(it)
            }
        }
        return res
    }
}