package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import org.junit.Test

class DiffPrinterTest {
    val dmp = DiffMatchPatch()

    @Test
    fun should_print_diff_to_a_readable_console_message() {
        val firstJson = """{"name":"gabriel","id":5}"""
        val secondJson = """{"name":"andres","id":5}"""

        val snapshotName = "should print diff to a readable console message"
        val diffs = dmp.diffMain(firstJson, secondJson)
        val msg = DiffPrinter.toReadableConsoleMessage(snapshotName, diffs)

        msg.matchWithSnapshot(snapshotName)
    }
}
