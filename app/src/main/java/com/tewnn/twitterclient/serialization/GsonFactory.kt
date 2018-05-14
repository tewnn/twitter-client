package com.tewnn.twitterclient.serialization

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.joda.time.DateTime

class GsonFactory {
    fun createGson(): Gson =
            GsonBuilder()
                    .registerTypeAdapter(DateTime::class.java, DateTimeConverter())
                    .create()
}