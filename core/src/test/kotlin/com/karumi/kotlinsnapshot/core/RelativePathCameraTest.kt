package com.karumi.kotlinsnapshot.core

import org.junit.Test

class RelativePathCameraTest {
    private val camera = Camera(relativePath = "src/test/kotlin/com/karumi/kotlinsnapshot/core")

    @Test
    fun should_take_snapshots_and_store_them_in_the_provided_relative_path() {
        val json = """{"name":"gabriel","id":5}"""
        camera.matchWithSnapshot("should take snapshot of a json string", json)
    }
}
