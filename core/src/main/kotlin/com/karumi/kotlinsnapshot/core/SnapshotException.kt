package com.karumi.kotlinsnapshot.core

import name.fraser.neil.plaintext.diff_match_patch

class SnapshotException(val diffs: List<diff_match_patch.Diff>, msg: String) : Exception(msg)
