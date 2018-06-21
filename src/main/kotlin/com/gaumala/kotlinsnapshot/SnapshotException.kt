package com.gaumala.kotlinsnapshot

import name.fraser.neil.plaintext.diff_match_patch

/**
 * Created by gabriel on 3/24/18.
 */

class SnapshotException(val diffs: List<diff_match_patch.Diff>, msg: String) : Exception(msg)
