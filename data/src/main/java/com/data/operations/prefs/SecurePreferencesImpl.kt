package com.data.operations.prefs

import android.content.Context
import com.commons.json.JsonParser
import com.data.BuildConfig

class SecurePreferencesImpl (context: Context, private val keyStoreManager: KeyStoreManager, private val jsonParser: JsonParser) : SecurePreferences {
    private val sharedPreferences = context.getSharedPreferences(BuildConfig.PREFS, Context.MODE_PRIVATE)

    init {
        keyStoreManager.createKeys(context, BuildConfig.KEY)
    }

    override fun put(key: String, value: String) {
        sharedPreferences?.edit()?.run {
            putString(keyStoreManager.encryptKey(key), keyStoreManager.encrypt(BuildConfig.KEY, value))
                    .apply()
        }
    }

    override fun put(key: String, value: Int) {
        put(key, value.toString())
    }

    override fun put(key: String, value: Boolean) {
        put(key, value.toString())
    }

    override fun put(key: String, value: Double) {
        put(key, value.toString())
    }

    override fun put(key: String, value: Float) {
        put(key, value.toString())
    }

    override fun <T : Any> put(key: String, value: T) {
        put(key, jsonParser.toJson(value))
    }

    override fun readString(key: String?): String? {
        val keyEncripted = keyStoreManager.encryptKey(key ?: "")
        var value = getStringFromLocal(keyEncripted)
        if (value.isNotBlank()) {
            value = keyStoreManager.decrypt(BuildConfig.KEY, value)
        }

        return value
    }

    override fun <T : Any> read(key: String?, type: Class<T>): T? {
        val value: String = readString(key) ?: return null

        return null
    }

    fun getStringFromLocal(keyEncripted: String) = sharedPreferences?.getString(keyEncripted, "") ?: ""

    override fun containsKey(key: String?): Boolean = sharedPreferences.contains(keyStoreManager.encryptKey(key ?: ""))

    override fun removeKey(key: String) {
        sharedPreferences.edit().run {
            remove(keyStoreManager.encryptKey(key))
        }.apply()
    }


}