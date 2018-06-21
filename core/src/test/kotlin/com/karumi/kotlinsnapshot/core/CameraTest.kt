package com.karumi.kotlinsnapshot.core

import org.junit.Test

class CameraTest {

    private val camera = Camera()

    data class User(val id: Int, val name: String)

    @Test
    fun should_take_snapshots_of_data_classes() {
        val u1 = User(1, "gabriel")
        camera.matchWithSnapshot("should take snapshot of a data class", u1)
    }

    @Test
    fun should_take_snapshots_of_collections() {
        val list = listOf(
            User(1, "gabriel"),
            User(2, "andres"),
            User(3, "miguel"))
        camera.matchWithSnapshot("should take snapshot of a list", list)
    }

    @Test
    fun should_take_snapshots_of_maps() {
        val map = mapOf(
            Pair(1, User(1, "gabriel")),
            Pair(2, User(2, "andres")),
            Pair(3, User(3, "miguel")))
        camera.matchWithSnapshot("should take snapshot of a map", map)
    }

    @Test
    fun should_take_snapshots_of_json_strings() {
        val json = """{"name":"gabriel","id":5}"""
        camera.matchWithSnapshot("should take snapshot of a json string", json)
    }
}
