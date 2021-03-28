package com.commons.lang

/**
 * Returns index of the first element matching the given [predicate], or null if the list does not contain such element.
 */
fun <T> List<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    val index: Int = this.indexOfFirst(predicate)
    if (index == -1) {
        return null
    }

    return index
}