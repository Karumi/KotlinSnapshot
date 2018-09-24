package com.karumi.kotlinsnapshot

import com.karumi.kotlinsnapshot.core.Camera
import com.karumi.kotlinsnapshot.core.KotlinSerialization
import com.karumi.kotlinsnapshot.core.SerializationModule
import com.karumi.kotlinsnapshot.core.TestCaseExtractor

class KotlinSnapshot(
    serializationModule: SerializationModule = KotlinSerialization(),
    testClassAsDirectory: Boolean = false,
    snapshotsFolder: String = ""
) {
    companion object {
        operator fun invoke(
            snapshotsFolder: String = "",
            testClassAsDirectory: Boolean = false
        ): KotlinSnapshot = KotlinSnapshot(
            KotlinSerialization(),
            testClassAsDirectory,
            snapshotsFolder
        )
    }

    private val camera = Camera(
        serializationModule,
        TestCaseExtractor(),
        testClassAsDirectory,
        snapshotsFolder
    )

    fun matchWithSnapshot(value: Any, snapshotName: String? = null) {
        camera.matchWithSnapshot(value, snapshotName)
    }
}

private val camera = Camera(KotlinSerialization(), TestCaseExtractor())

fun Any.matchWithSnapshot(snapshotName: String? = null) {
    camera.matchWithSnapshot(this, snapshotName)
}
