package com.karumi.kotlinsnapshot.core

import java.lang.reflect.Method

internal open class TestCaseExtractor {

    companion object {
        private val TEST_ANNOTATION_EXPRESSION = Regex("org.junit(?s)(.*).Test")
    }

    open fun getTestStackElement(): StackTraceElement? {
        val stackTrace = Thread.currentThread().stackTrace
        return stackTrace.toList().firstOrNull { trace ->
            try {
                val traceClass = Class.forName(trace.className)
                val method = traceClass.getMethod(trace.methodName)
                isTestMethod(method)
            } catch (exception: Exception) {
                false
            }
        }
    }

    private fun isTestMethod(method: Method): Boolean =
        method.annotations.any {
            it.annotationClass.qualifiedName?.matches(TEST_ANNOTATION_EXPRESSION) ?: false
        }
}
