package com.hearing.aitchat.common

/**
 * @author liujiadong
 * @since 2020/10/20
 */
open class Range(var from: Int, var to: Int) : Comparable<Range> {
    fun isWrapped(start: Int, end: Int) = from >= start && to <= end

    fun isWrappedBy(start: Int, end: Int) = start in (from + 1) until to || end in (from + 1) until to

    fun contains(start: Int, end: Int) = from <= start && to >= end

    fun isEqual(start: Int, end: Int) = from == start && to == end || from == end && to == start

    fun getAnchorPosition(value: Int) = if (value - from - (to - value) >= 0) to else from

    fun setOffset(offset: Int) {
        from += offset
        to += offset
    }

    override fun compareTo(other: Range) = from - other.from
}