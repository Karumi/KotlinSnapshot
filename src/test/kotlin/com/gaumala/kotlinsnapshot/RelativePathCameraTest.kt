package com.gaumala.kotlinsnapshot

import org.junit.Test

/**
 * Created by gabriel on 3/25/18.
 */
class RelativePathCameraTest {
    private val camera = Camera(relativePath = "src/test/kotlin/com/kotlinsnapshot")

    @Test
    fun should_take_snapshots_and_store_them_in_the_provided_relative_path() {
        val json = """{"name":"gabriel","id":5}"""
        camera.matchWithSnapshot("should take snapshot of a json string", json)
    }
}
