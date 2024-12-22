package com.jonivan.aoc.y2024

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils.Companion.readFileAsString

class Day03: Solution<String>() {

    private val mulRegex = Regex("mul\\((\\d+),(\\d+)\\)")
    private val doRegex = Regex("do\\(\\)")
    private val noDoRegex = Regex("^(.*?)don't\\(\\)")
    private val doLastRegex = Regex("do\\(\\)(.*?)$")
    private val digitRegex = Regex("\\d+")

    override fun partOne(input: String): Any {
        val allMul = mulRegex.findAll(input)

        val result: Long = allMul.sumOf { mul ->
            val digits = digitRegex.findAll(mul.value).map { it.value.toLong() }.toList()
            digits[0] * digits[1]
        }

        return result
    }

    override fun partTwo(input: String): Any {
        val result = input
            .split("do()")
            .asSequence()
            .map { track -> track.split("don't()")[0] }
            .map { s -> mulRegex.findAll(s).toList() }.flatMap { matches -> matches }
            .map { match ->
                val (x, y) = match.destructured
                x.toInt() * y.toInt()
            }
            .sum()

        return result
    }
}

fun main() {
    val input = readFileAsString("src/main/resources/2024/input_day_03.txt")
    val day = Day03()
    day.solvePart1(input)
    day.solvePart2(input)
}