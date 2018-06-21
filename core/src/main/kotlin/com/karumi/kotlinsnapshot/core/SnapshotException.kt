package com.karumi.kotlinsnapshot.core

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch

class SnapshotException(val diffs: List<DiffMatchPatch.Diff>, msg: String) : Exception(msg)
