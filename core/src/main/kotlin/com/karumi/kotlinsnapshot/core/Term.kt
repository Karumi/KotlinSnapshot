package com.karumi.kotlinsnapshot.core

internal object Term {
    private val RESET = "\u001B[0m"
    private val GREEN = "\u001B[32m"
    private val RED = "\u001B[31m"

    fun green(s: String): String = "$GREEN$s$RESET"
    fun red(s: String): String = "$RED$s$RESET"
}
