package com.hearing.aitchat.selector

import com.hearing.aitchat.ait.User

/**
 * @author liujiadong
 * @since 2020/10/21
 */
object UserFactory {
    private val users = mutableListOf<User>()

    init {
        for (i in 0..20) {
            users.add(User("$i", "hearing-$i"))
        }
    }

    fun getUsersByKey(key: String?): List<User> {
        if (key == null) {
            return users
        }
        val res = mutableListOf<User>()
        users.forEach {
            if (it.name.contains(key)) {
                res.add(it)
            }
        }
        return res
    }
}