package com.karumi.kotlinsnapshot.core

import com.google.gson.GsonBuilder
import com.karumi.kotlinsnapshot.core.serializers.RuntimeClassNameTypeAdapterFactory
import java.text.DateFormat

interface SerializationModule {

    fun serialize(value: Any?): String
}

class KotlinSerialization : SerializationModule {

    companion object {
        private val gson = GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat(DateFormat.FULL, DateFormat.FULL)
            .registerTypeAdapterFactory(RuntimeClassNameTypeAdapterFactory.of(Object::class.java))
            .create()
    }

    override fun serialize(value: Any?): String =
        gson.toJson(value)
}
