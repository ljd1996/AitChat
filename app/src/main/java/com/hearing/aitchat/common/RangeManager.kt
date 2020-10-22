package com.hearing.aitchat.common

/**
 * @author liujiadong
 * @since 2020/10/20
 */
class RangeManager {
    private val mRanges = mutableListOf<Range>()

    fun getSorted(): List<Range> {
        mRanges.sort()
        return mRanges
    }

    fun <T : Range> add(range: T) = mRanges.add(range)

    fun <T : Range> remove(range: T) = mRanges.remove(range)

    fun clear() = mRanges.clear()

    fun isEmpty() = mRanges.isEmpty()

    operator fun iterator() = mRanges.iterator()

    fun getRangeOfLeft(sel: Int): Range? {
        for (range in mRanges) {
            if (range.to == sel) {
                return range
            }
        }
        return null
    }

    fun getRangeOfNearby(selStart: Int, selEnd: Int): Range? {
        for (range in mRanges) {
            if (range.isWrappedBy(selStart, selEnd)) {
                return range
            }
        }
        return null
    }
}