package com.hearing.aitchat.ait

import com.hearing.aitchat.common.IEnhanceData

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class User(val id: String, val name: String) : IEnhanceData {
    companion object {
        const val AIT = "@"
        const val USER = "user"
        const val ID = "id"
        const val NAME = "name"
        const val COLOR = "color"
        private const val USER_FORMAT = "<$USER $ID='%s' $NAME='%s' $COLOR='%s'>%s</$USER>"
    }

    override fun charSequence() = "$AIT$name "

    override fun color() = "#FF0000"

    override fun formatData() = String.format(USER_FORMAT, id, name, color(), name)
}