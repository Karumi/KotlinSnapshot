package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.matchWithSnapshot
import junit.framework.TestCase.fail
import org.junit.Test

class CameraTest {

    companion object {
        private const val WILL_NOT_MATCH = "will not match"
    }

    data class User(val id: Int, val name: String)

    @Test
    fun should_take_snapshots_of_data_classes() {
        val u1 = User(1, "gabriel")
        u1.matchWithSnapshot("should take snapshot of a data class")
    }

    @Test
    fun should_take_snapshots_of_collections() {
        val list = listOf(
            User(1, "gabriel"),
            User(2, "andres"),
            User(3, "miguel"))
        list.matchWithSnapshot("should take snapshot of a list")
    }

    @Test
    fun should_take_snapshots_of_maps() {
        val map = mapOf(
            Pair(1, User(1, "gabriel")),
            Pair(2, User(2, "andres")),
            Pair(3, User(3, "miguel")))
        map.matchWithSnapshot("should take snapshot of a map")
    }

    @Test
    fun should_take_snapshots_of_json_strings() {
        val json = """{"name":"gabriel","id":5}"""
        json.matchWithSnapshot("should take snapshot of a json string")
    }

    @Test
    fun should_take_snapshots_of_null_values() {
        val nullString: String? = null
        nullString.matchWithSnapshot("should take snapshots of null values")
    }

    @Test(expected = SnapshotException::class)
    fun should_throw_snapshot_exception_when_not_match() {
        WILL_NOT_MATCH.matchWithSnapshot("should throw snapshot exception when not match")
    }

    @Test
    fun `should_throw_snapshot_exception_with_junit_diff_message`() {
        try {
            WILL_NOT_MATCH.matchWithSnapshot("should throw snapshot exception when not match")
            fail("should throw snapshot exception to match actual value")
        } catch (snapshotException: SnapshotException) {
            snapshotException.message!!.matchWithSnapshot()
        }
    }

    @Test
    fun should_not_throw_snapshot_exception_because_of_Windows_line_endings() {
        val stringWithWindowsLineEndings = "Line 1\nLine 2\n"
        stringWithWindowsLineEndings.matchWithSnapshot(
            "should not throw snapshot exception because of Windows line endings"
        )
    }
}
