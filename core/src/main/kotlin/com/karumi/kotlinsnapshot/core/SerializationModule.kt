package com.karumi.kotlinsnapshot.core

import java.time.ZoneId
import java.time.temporal.Temporal
import java.util.Date
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties

interface SerializationModule {

    fun serialize(value: Any?): String
}

class KotlinSerialization : SerializationModule {

    override fun serialize(value: Any?): String = when {
        value == null -> "null"
        isPrimitive(value) -> value.toString()
        value is CharSequence -> value.toString()
        value::class.java.isEnum -> value.toString()
        value is Iterable<*> -> iterableToString(value)
        value is Array<*> -> iterableToString(value.toList())
        value is Map<*, *> -> mapToString(value)
        value is Pair<*, *> -> pairToString(value)
        value is Date -> dateToString(value)
        value is Temporal -> value.toString()
        else -> toString(value)
    }

    private fun dateToString(value: Date): String =
        value.toInstant()
            .atZone(ZoneId.of("Z"))
            .toLocalDateTime()
            .toString()

    private fun isPrimitive(value: Any) =
        value::class.javaPrimitiveType?.isPrimitive ?: false

    private fun toString(value: Any): String {
        val anyClass = value::class
        val className = anyClass.simpleName
        val fieldValueList = anyClass.memberProperties
            .sortedBy { it.name }
            .joinToString { field ->
                getFieldValuePair(value, field)
            }
        val classBody = addParenthesesIfNotEmpty(fieldValueList)
        return "$className$classBody"
    }

    private fun iterableToString(list: Iterable<*>): String =
        list.joinToString(", ", "[", "]") {
            serialize(it)
        }

    private fun mapToString(map: Map<*, *>): String = map
        .mapKeys { serialize(it.key) }
        .toSortedMap()
        .mapValues { serialize(it.value) }
        .toString()

    private fun pairToString(value: Pair<*, *>): String =
        "(${serialize(value.first)}, ${serialize(value.second as Any)})"

    private fun getFieldValuePair(value: Any, field: KProperty1<out Any, Any?>): String =
        if (field.visibility == KVisibility.PUBLIC) {
            val serialize = serialize(field.getter.call(value))
            "${field.name}=$serialize"
        } else {
            ""
        }

    private fun addParenthesesIfNotEmpty(fieldValueList: String): String =
        if (fieldValueList.isNotEmpty()) {
            "($fieldValueList)"
        } else {
            ""
        }
}
