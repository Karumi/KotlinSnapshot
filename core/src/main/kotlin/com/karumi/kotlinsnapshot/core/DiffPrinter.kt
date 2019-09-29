package com.karumi.kotlinsnapshot.core

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import java.util.LinkedList

internal object DiffPrinter {
    private val dmp = DiffMatchPatch()

    private fun printDiff(diff: DiffMatchPatch.Diff): String =
        when (diff.operation) {
            DiffMatchPatch.Operation.EQUAL, null -> Term.white(" ${diff.text}\n")
            DiffMatchPatch.Operation.DELETE -> Term.red("- ${diff.text}\n")
            DiffMatchPatch.Operation.INSERT -> Term.green("+ ${diff.text}\n")
        }

    fun toReadableConsoleMessage(
        snapshotName: String,
        diffs: LinkedList<DiffMatchPatch.Diff>
    ): String {
        val sb = StringBuilder(
            "Received value does not match stored snapshot: \"$snapshotName\"\n\n"
        )

        dmp.diffCleanupSemantic(diffs)
        diffs.forEach { diff -> sb.append(printDiff(diff)) }
        sb.append("\n\n")
        return sb.toString()
    }
}
