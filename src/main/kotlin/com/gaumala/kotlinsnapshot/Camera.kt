package com.gaumala.kotlinsnapshot

import name.fraser.neil.plaintext.diff_match_patch
import java.io.File
import java.nio.file.Paths

/**
 * Created by gabriel on 3/24/18.
 */

class Camera(relativePath: String) {
    private val snapshotDir: File
    private val dmp = diff_match_patch()

    init {
        snapshotDir = createSnapshotDir(relativePath)
        purgeSnapshotsIfNeeded(snapshotDir)
    }

    constructor() : this("")

    private val shouldUpdateSnapshots: Boolean by lazy {
        System.getProperty("updateSnapshots") == "1"
    }

    private fun differsFromSnapshot(diffs: List<diff_match_patch.Diff>): Boolean =
        diffs.find { diff -> diff.operation != diff_match_patch.Operation.EQUAL } != null

    private fun matchValueWithExistingSnapshot(snapshotFile: File, value: Any) {
        val snapshotContents = snapshotFile.readText()
        val valueString = value.toString()
        val diffs = dmp.diff_main(snapshotContents, valueString)
        val hasChanged = differsFromSnapshot(diffs)
        if (hasChanged && shouldUpdateSnapshots)
            writeSnapshot(true, snapshotFile, value)
        else if (hasChanged) {
            val msg = DiffPrinter.toReadableConsoleMessage(snapshotFile.name, diffs)
            throw SnapshotException(diffs, msg)
        }
    }

    private fun writeSnapshot(update: Boolean, snapshotFile: File, value: Any) {
        snapshotFile.writeText(value.toString())
        val fileName = "\"${snapshotFile.name}\""
        val msg = if (update) "${Term.green("1 snapshot, $fileName,")} updated."
        else "${Term.green("1 snapshot, $fileName,")} written."
        println(msg)
    }

    fun matchWithSnapshot(snapshotName: String, value: Any) {
        val snapshotFile = File(snapshotDir, "$snapshotName.snap")
        if (snapshotFile.exists())
            matchValueWithExistingSnapshot(snapshotFile, value)
        else
            writeSnapshot(false, snapshotFile, value)
    }

    private companion object {
        private val purgedDirectories = HashSet<String>()
        fun createSnapshotDir(relativePath: String): File {
            val dir = System.getProperty("user.dir")
            val snapshotDirPath = Paths.get(dir, relativePath, "__snapshot__").toString()
            val snapshotDir = File(snapshotDirPath)
            snapshotDir.mkdirs()
            return snapshotDir
        }

        fun purgeSnapshotsIfNeeded(snapshotDir: File) {
            val pathToPurge = snapshotDir.absolutePath
            val shouldPurge = System.getProperty("purgeSnapshots") == "1" &&
                !purgedDirectories.contains(pathToPurge)

            if (shouldPurge) {
                snapshotDir.deleteAllContainedFiles()
                purgedDirectories.add(pathToPurge)
            }
        }
    }
}
