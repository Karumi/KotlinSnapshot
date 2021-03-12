package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.junit.Test

class ArrayListTest {

    @Test
    fun `should serialize any class with an empty list inside`() {
        val input = TestObject(emptyList())

        input.matchWithSnapshot()
    }

    @Test
    fun `should serialize any class with a non empty list inside`() {
        val input = TestObject(listOf("item1", "item2"))

        input.matchWithSnapshot()
    }

    data class TestObject(val list: List<String>)
}
