package com.karumi.kotlinsnapshot.core

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import java.util.LinkedList

internal object DiffPrinter {
    private val dmp = DiffMatchPatch()

    private fun printDiff(diff: DiffMatchPatch.Diff): String =
        when (diff.operation) {
            DiffMatchPatch.Operation.EQUAL, null -> " ${diff.text}\n"
            DiffMatchPatch.Operation.DELETE -> Term.green("- ${diff.text}\n")
            DiffMatchPatch.Operation.INSERT -> Term.red("+ ${diff.text}\n")
        }

    fun toReadableConsoleMessage(
        snapshotName: String,
        diffs: LinkedList<DiffMatchPatch.Diff>
    ): String {
        val sb = StringBuilder("${Term.red("Received value")} does not match ${
        Term.green("stored snapshot: \"$snapshotName\"")}\n\n${
        Term.green("-Snapshot")}\n${Term.red("+Received")}\n\n")

        dmp.diffCleanupSemantic(diffs)
        diffs.forEach { diff -> sb.append(printDiff(diff)) }
        return sb.toString()
    }
}
