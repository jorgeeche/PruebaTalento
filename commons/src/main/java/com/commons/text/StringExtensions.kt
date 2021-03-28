package com.commons.text

import java.util.*

fun String.getBeginIndexesOf(text: String): List<Int> {
    if (text.isEmpty()) {
        return emptyList()
    }

    val beginIndexes: MutableList<Int> = mutableListOf()

    var temporalText: String = this

    while (true) {
        if (!temporalText.contains(text)) {
            return beginIndexes
        }

        val beginIndex: Int = if (beginIndexes.isEmpty()) {
            temporalText.indexOf(text)
        } else {
            beginIndexes.last() + text.length + temporalText.indexOf(text)
        }
        beginIndexes.add(beginIndex)

        temporalText = temporalText.substringAfter(text)
    }
}


fun String.isGreaterVersionThan(version: String): Boolean {
    try {
        val targetVersion = StringTokenizer(this, ".")
        val currentVersion = StringTokenizer(version, ".")

        while (currentVersion.hasMoreTokens()) {
            val currentIntegerVersion = currentVersion.nextToken().toInt()
            val targetIntegerVersion = targetVersion.nextToken().toInt()

            if (currentIntegerVersion > targetIntegerVersion) {
                return false
            } else if (currentIntegerVersion < targetIntegerVersion) {
                return true
            }
        }

    } catch (e: Exception) {
        return false
    }

    return false
}

