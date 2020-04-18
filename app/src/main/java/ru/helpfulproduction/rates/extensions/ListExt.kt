package ru.helpfulproduction.rates.extensions

fun<T> List<T>.setItemAsFirst(predicate: (T) -> Boolean): List<T> {
    val position = indexOfFirst(predicate)
    return setItemAsFirst(position)
}

fun<T> List<T>.setItemAsFirst(position: Int): List<T> {
    if (position < 0 || position >= size) {
        return this
    }
    val value = get(position)
    return toMutableList().apply {
        removeAt(position)
        add(0, value)
    }
}