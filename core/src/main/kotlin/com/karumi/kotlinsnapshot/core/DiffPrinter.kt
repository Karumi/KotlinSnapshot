package com.karumi.kotlinsnapshot.core

import name.fraser.neil.plaintext.diff_match_patch
import java.util.LinkedList

internal object DiffPrinter {
    private val dmp = diff_match_patch()

    private fun printDiff(diff: diff_match_patch.Diff): String =
        when (diff.operation) {
            diff_match_patch.Operation.EQUAL, null -> " ${diff.text}\n"
            diff_match_patch.Operation.DELETE -> Term.green("- ${diff.text}\n")
            diff_match_patch.Operation.INSERT -> Term.red("+ ${diff.text}\n")
        }

    fun toReadableConsoleMessage(
        snapshotName: String,
        diffs: LinkedList<diff_match_patch.Diff>
    ): String {
        val sb = StringBuilder("${Term.red("Received value")} does not match ${
        Term.green("stored snapshot: \"$snapshotName\"")}\n\n${
        Term.green("-Snapshot")}\n${Term.red("+Received")}\n\n")

        dmp.diff_cleanupSemantic(diffs)
        diffs.forEach { diff -> sb.append(printDiff(diff)) }
        return sb.toString()
    }
}
