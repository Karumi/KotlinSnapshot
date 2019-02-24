package com.karumi.kotlinsnapshot.core

import junit.framework.ComparisonFailure
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch

class SnapshotException(
    val diffs: List<DiffMatchPatch.Diff>,
    msg: String,
    expected: String,
    actual: String
) : ComparisonFailure(msg, expected, actual)
