package com.data.operations.prefs;

interface SecurePreferences {
    fun put(key: String, value: String)
    fun put(key: String, value: Int)
    fun put(key: String, value: Boolean)
    fun put(key: String, value: Double)
    fun put(key: String, value: Float)
    fun <T : Any> put(key: String, value: T)

    fun readString(key: String?): String?
    fun <T : Any> read(key: String?, type: Class<T>): T?

    fun containsKey(key: String?): Boolean

    fun removeKey(key: String)

}
