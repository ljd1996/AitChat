package com.hearing.aitchat.edit

import android.graphics.Color

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class User(val id: String, val name: String) : IInsertData {
    override fun charSequence() = "@$name"

    override fun formatData(): FormatRange.FormatData = UserConvert(this)

    override fun color() = Color.RED

    private class UserConvert(private val user: User) : FormatRange.FormatData {

        companion object {
            private const val USER_FORMAT = "<user id='%s' name='%s'>%s</user>"
        }

        override fun formatCharSequence(): CharSequence {
            return String.format(USER_FORMAT, user.id, user.name, user.name)
        }
    }
}