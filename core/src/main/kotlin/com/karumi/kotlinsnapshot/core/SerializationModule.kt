package com.karumi.kotlinsnapshot.core

import com.google.gson.GsonBuilder
import com.karumi.kotlinsnapshot.core.serializers.RuntimeClassNameTypeAdapterFactory
import com.karumi.kotlinsnapshot.serializers.GsonUTCDateAdapter
import java.util.Date

interface SerializationModule {

    fun serialize(value: Any?): String
}

class KotlinSerialization : SerializationModule {

    companion object {

        val gsonBuilder: GsonBuilder = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Date::class.java, GsonUTCDateAdapter())
            .registerTypeAdapterFactory(RuntimeClassNameTypeAdapterFactory.of(Object::class.java))

        private val gson = gsonBuilder.create()
    }

    override fun serialize(value: Any?): String = when (value) {
        is String -> value
        else -> gson.toJson(value)
    }
}
