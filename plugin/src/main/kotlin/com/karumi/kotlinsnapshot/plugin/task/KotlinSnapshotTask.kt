package com.karumi.kotlinsnapshot.plugin.task

import com.karumi.kotlinsnapshot.plugin.GroovyHack
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

sealed class KotlinSnapshotTask : DefaultTask() {

    init {
        group = "KotlinSnapshot"
    }

    open class UpdateSnapshots : KotlinSnapshotTask() {
        companion object {
            const val name = "updateSnapshots"
        }

        init {
            description = "Run all tests updating snapshots when differences are found."
        }

        @TaskAction
        fun updateSnapshots() {
            GroovyHack.configureProjectForSnapshotUpdate(project)
        }
    }

    open class PurgeSnapshots : KotlinSnapshotTask() {
        companion object {
            const val name = "purgeSnapshots"
        }

        init {
            description = "Delete all snapshots and update them all"
        }

        @TaskAction
        fun purgeSnapshots() {
            GroovyHack.configureProjectForSnapshotPurge(project)
        }
    }
}
