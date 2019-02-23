package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.junit.jupiter.api.Test

class InferenceNameJUnit5Test {

    @Test
    fun the_snap_test_name_will_be_inferred_in_Junit_5_test_cases_named_with_test_if_it_is_not_specified(
    ) {
        val pedro = Developer("A", 3)
        pedro.matchWithSnapshot()
    }

    @Test
    fun `the snap test name will be inferred even if uses spaces in JUnit5`() {
        val toni = Developer("Toni", 1)
        toni.matchWithSnapshot()
    }
}

class InferenceNameJUnit5Spec {

    @Test
    fun the_snap_test_name_will_be_inferred_in_Junit_5_test_cases_named_with_spec_if_it_is_not_specified(
    ) {
        val sergio = Developer("Sergio", 2)
        sergio.matchWithSnapshot()
    }
}
