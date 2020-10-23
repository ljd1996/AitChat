package com.hearing.aitchat.common

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class RangeManager {
    private val ranges = mutableListOf<Range>()

    fun getSorted(): List<Range> {
        ranges.sort()
        return ranges
    }

    fun <T : Range> add(range: T) = ranges.add(range)

    fun <T : Range> remove(range: T) = ranges.remove(range)

    fun clear() = ranges.clear()

    fun isEmpty() = ranges.isEmpty()

    operator fun iterator() = ranges.iterator()

    fun contains(sel: Int): Boolean {
        ranges.forEach { range ->
            if (range.contains(sel, sel)) return true
        }
        return false
    }

    fun getRangeOfLeft(sel: Int): Range? {
        ranges.forEach { range ->
            if (range.to == sel) return range
        }
        return null
    }

    fun getRangeOfNearby(selStart: Int, selEnd: Int): Range? {
        ranges.forEach { range ->
            if (range.isWrappedBy(selStart, selEnd)) return range
        }
        return null
    }
}