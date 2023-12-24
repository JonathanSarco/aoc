package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils

private fun calculateAsciiValue(acc: Int, char: Char): Int {
    var ascii = char.code
    // add acum
    ascii += acc
    // multiplied
    ascii *= 17
    // remainder
    ascii %= 256
    return ascii
}

private fun partOne(input: String) {
    val commaSeparated = input.split(",")

    val result = commaSeparated.sumOf {
        val r = it.fold(0) { acc, i -> calculateAsciiValue(acc, i) }
        r
    }
    println("P1 result: $result")
}

private fun partTwo(input: String) {
    val commaSeparated = input.split(",")

    val map = buildMap<String, Int>() {
        for (l in commaSeparated)
            if (l.contains('-'))
                remove(l.substringBefore('-'))
            else
                l.split('=')
                .let { (a, b) -> this[a] = b.toInt() }
    }

    val result = map
        .entries
        .groupBy { it.key.fold(0) { acc, i -> calculateAsciiValue(acc, i) } }
        .map { (it.key + 1) * it.value.foldIndexed(0) { i, acc, lens -> acc + (i + 1) * lens.value } }
        .sum()

    println("P2 result: $result")
}

fun main() {
    val input = InputUtils.readFileAsString("src/main/resources/2023/input_day_15.txt")
    partOne(input)
    partTwo(input)
}