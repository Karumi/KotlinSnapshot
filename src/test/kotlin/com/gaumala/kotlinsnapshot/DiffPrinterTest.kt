package com.gaumala.kotlinsnapshot

import name.fraser.neil.plaintext.diff_match_patch
import org.junit.Test

/**
 * Created by gabriel on 3/25/18.
 */

class DiffPrinterTest {
    val dmp = diff_match_patch()
    val camera = Camera()

    @Test
    fun should_print_diff_to_a_readable_console_message() {
        val firstJson = """{"name":"gabriel","id":5}"""
        val secondJson = """{"name":"andres","id":5}"""

        val snapshotName = "should print diff to a readable console message"
        val diffs = dmp.diff_main(firstJson, secondJson)
        val msg = DiffPrinter.toReadableConsoleMessage(snapshotName, diffs)

        camera.matchWithSnapshot(snapshotName = snapshotName, value = msg)
    }
}
