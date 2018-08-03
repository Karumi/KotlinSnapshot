package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.KotlinSnapshot
import org.junit.Test

class RelativePathTest {
    private val kotlinSnapshot =
        KotlinSnapshot(snapshotsFolder = "src/test/kotlin/com/karumi/kotlinsnapshot/core")

    @Test
    fun should_take_snapshots_and_store_them_in_the_provided_relative_path() {
        val json = """{"name":"gabriel","id":5}"""
        kotlinSnapshot.matchWithSnapshot(json, "should take snapshot of a json string")
    }
}
