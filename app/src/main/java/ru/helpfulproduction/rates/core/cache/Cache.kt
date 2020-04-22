package ru.helpfulproduction.rates.core.cache

interface Cache<T> {
    var cached: T?
    fun save(items: T)
    fun get(): T?
}