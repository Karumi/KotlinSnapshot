package com.karumi.kotlinsnapshot

import com.karumi.kotlinsnapshot.core.Camera

class KotlinSnapshot(snapshotsFolder: String = "") {

    private val camera = Camera(snapshotsFolder)

    fun matchWithSnapshot(value: Any, snapshotName: String? = null) {
        camera.matchWithSnapshot(snapshotName, value)
    }
}

private val camera = Camera()

fun Any.matchWithSnapshot(snapshotName: String? = null) {
    camera.matchWithSnapshot(snapshotName, this)
}
