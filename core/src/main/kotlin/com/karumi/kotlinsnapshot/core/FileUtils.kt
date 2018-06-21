package com.karumi.kotlinsnapshot.core

import java.io.File

internal fun File.deleteAllContainedFiles() {
    this.listFiles().forEach { it.delete() }
}
