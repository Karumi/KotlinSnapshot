package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.core.exceptions.TestNameNotFoundException
import org.junit.Test

class InferenceNameTest {

    private val camera = Camera()

    @Test
    fun the_snap_test_name_will_be_inferred_in_test_cases_named_with_test_if_it_is_not_specified(
    ) {
        val pedro = Developer("Pedro", 3)
        camera.matchWithSnapshot(pedro)
    }
}

class InferenceNameSpec {
    private val camera = Camera()

    @Test
    fun the_snap_test_name_will_be_inferred_in_test_cases_named_with_spec_if_it_is_not_specified(
    ) {
        val pedro = Developer("Sergio", 2)
        camera.matchWithSnapshot(pedro)
    }
}

class InvalidClassName {
    private val camera = Camera()

    @Test(expected = TestNameNotFoundException::class)
    fun if_the_test_name_can_not_be_found_and_exception_will_be_thrown() {
        val pedro = Developer("Fran", 1)
        camera.matchWithSnapshot(pedro)
    }
}

data class Developer(val name: String, val yearsInTheCompany: Int)
