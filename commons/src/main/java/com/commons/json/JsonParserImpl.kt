package com.commons.json

import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.*

class JsonParserImpl :JsonParser{
    private val gson: Gson = Gson()

    override fun <T> fromJson(json: String, type: Class<T>): T = gson.fromJson(json, type)

    override fun <T : Any> toJson(value: T): String = gson.toJson(value)
}