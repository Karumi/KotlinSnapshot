package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.exceptions.TestNameNotFoundException
import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.junit.Test

class InferenceNameTest {

    @Test
    fun the_snap_test_name_will_be_inferred_in_test_cases_named_with_test_if_it_is_not_specified() {
        val pedro = Developer("Pedro", 3)
        pedro.matchWithSnapshot()
    }

    @Test
    fun `the snap test name will be inferred even if uses spaces`() {
        val toni = Developer("Toni", 1)
        toni.matchWithSnapshot()
    }
}

class InferenceNameSpec {

    @Test
    fun the_snap_test_name_will_be_inferred_in_test_cases_named_with_spec_if_it_is_not_specified() {
        val sergio = Developer("Sergio", 2)
        sergio.matchWithSnapshot()
    }
}

class InferenceNameNotSupported {

    val camera = Camera

    @Test(expected = TestNameNotFoundException::class)
    fun if_the_test_name_can_not_be_found_and_exception_will_be_thrown() {
        val fran = Developer("Fran", 1)
        fran.matchWithSnapshot()
    }
}

data class Developer(val name: String, val yearsInTheCompany: Int)
