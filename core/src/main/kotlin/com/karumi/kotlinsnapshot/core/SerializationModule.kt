package com.karumi.kotlinsnapshot.core

import aballano.kotlinmemoization.memoize
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

    override fun serialize(value: Any?): String =
        serializeObject(value, 0)

    private fun serializeObject(value: Any?, depth: Int): String = when {
        value == null -> "null"
        isPrimitive(value) -> prependIndentation(value.toString(), depth)
        value is CharSequence -> prependIndentation(value.toString(), depth)
        value::class.java.isEnum -> prependIndentation(value.toString(), depth)
        value is Iterable<*> -> iterableToString(value, depth)
        value is Array<*> -> iterableToString(value.toList(), depth)
        value is Map<*, *> -> mapToString(value, depth)
        value is Pair<*, *> -> pairToString(value, depth)
        value is Date -> prependIndentation(dateToString(value), depth)
        value is Temporal -> prependIndentation(value.toString(), depth)
        else -> prependIndentation(classToString(value), depth)
    }

    private fun dateToString(value: Date): String =
        value.toInstant()
            .atZone(ZoneId.of("Z"))
            .toLocalDateTime()
            .toString()

    private fun isPrimitive(value: Any) =
        value::class.javaPrimitiveType?.isPrimitive ?: false

    private fun classToString(value: Any): String {
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

    private fun iterableToString(list: Iterable<*>, depth: Int): String {
        val blockIndent = computeIndent(depth)
        val itemIndent = computeIndent(depth + 1)
        return list.joinToString(",\n$itemIndent", "$blockIndent[ ", " ]") {
            serializeObject(it, depth)
        }
    }

    private fun mapToString(map: Map<*, *>, depth: Int): String {
        val blockIndent = computeIndent(depth)
        val itemIndent = computeIndent(depth + 1)
        return map
            .mapKeys { serializeObject(it.key, depth) }
            .toSortedMap()
            .toList()
            .joinToString(",\n$itemIndent", "$blockIndent{ ", " }") { mapEntry ->
                val serializedKey = mapEntry.first
                val value = mapEntry.second
                val serializedValue = serializeObject(mapEntry.second, depth)
                if (needsNestedIndex(value)) {
                    val nestedIndent = computeIndent((serializedKey.length / 2) + 2)
                    val nestedSerializedValue = serializedValue.replace("\n", "\n$nestedIndent")
                    "$serializedKey=$nestedSerializedValue"
                } else {
                    "$serializedKey=$serializedValue"
                }
            }
    }

    private fun pairToString(value: Pair<*, *>, depth: Int): String =
        prependIndentation("(${serialize(value.first)}, ${serialize(value.second as Any)})", depth)

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

    private val computeIndent = { depth: Int -> (0..depth)
        .joinToString("  ") { _ -> "" } }.memoize()

    private fun prependIndentation(value: String, depth: Int): String =
        "${computeIndent(depth)}$value"

    private fun needsNestedIndex(value: Any?) = when (value) {
        is Iterable<*> -> true
        is Array<*> -> true
        is Map<*, *> -> true
        else -> false
    }
}
