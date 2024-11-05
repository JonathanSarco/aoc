package com.jonivan.aoc.utils

fun <T> Collection<T>.dropBlanks() = this.filter { it.toString().isNotBlank() }