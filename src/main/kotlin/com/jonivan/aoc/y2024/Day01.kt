package com.jonivan.aoc.y2024

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils.Companion.convertToListString
import java.io.File
import kotlin.math.abs

class Day01: Solution<List<String>>() {
    override fun partOne(input: List<String>): Any {
        val pairs = input.map { line ->
            val (first, second) = line.split("   ").map { it.toInt() }
            Pair(first, second)
        }

        val orderedPairs =
            pairs.map { it.first }.sorted()
                .zip(
                    pairs.map { it.second }.sorted()
                )

        val total: Long = orderedPairs.sumOf { abs(it.first - it.second).toLong() }
        return total
    }

    override fun partTwo(input: List<String>): Any {
        val pairs = input.map { line ->
            val (first, second) = line.split("   ").map { it.toInt() }
            Pair(first, second)
        }

        val result = pairs.sumOf { (first, _) ->
            val times = pairs.count { it.second == first }
            times * first
        }
        return result
    }
}

fun main() {
    val lines = File("src/main/resources/2024/input_day_01.txt").convertToListString()
    val day = Day01()
    day.solvePart1(lines)
    day.solvePart2(lines)
}