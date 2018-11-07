package com.karumi.kotlinsnapshot.serializers

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class GsonUTCDateAdapter : JsonSerializer<Date> {

    private val dateFormat: DateFormat

    init {
        dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    @Synchronized
    override fun serialize(
        date: Date,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement =
            JsonPrimitive(dateFormat.format(date))
}
