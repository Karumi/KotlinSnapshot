package com.gaumala.kotlinsnapshot

/**
 * Created by gabriel on 3/24/18.
 */
internal object Term {
    private val RESET = "\u001B[0m"
    private val GREEN = "\u001B[32m"
    private val RED = "\u001B[31m"

    fun green(s: String): String = "$GREEN$s$RESET"
    fun red(s: String): String = "$RED$s$RESET"
}
