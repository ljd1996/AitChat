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
        const val DEFAULT_COLOR = "#FF0000"
        private const val USER_FORMAT = "<$USER $ID='%s' $NAME='%s' $COLOR='%s'>%s</$USER>"
    }

    override fun label() = name

    override fun charSequence() = " $AIT$name "

    override fun color() = DEFAULT_COLOR

    override fun formatData() = String.format(USER_FORMAT, id, name, color(), charSequence())
}