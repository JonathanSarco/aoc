package com.jonivan.aoc.y2024

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils.Companion.convertToListString
import java.io.File
import kotlin.math.abs

class Day02: Solution<List<String>>() {
    private fun Long.isValidDifference(toCompare: Long): Boolean {
        val dif = abs(this - toCompare)
        val value = (dif in 1..3)
        return value
    }

    private fun isValidAdjacentDifference(list: List<Long>): Boolean {
        val totalAd = list.zipWithNext().all { (a, b) -> a.isValidDifference(b) }
        return totalAd
    }

    private fun isIncreasing(levels: List<Long>): Boolean {
        return levels.zipWithNext().all { (a, b) -> a <= b }
    }

    private fun isDecreasing(levels: List<Long>): Boolean {
        return levels.zipWithNext().all { (a, b) -> a >= b }
    }

    private fun removeLevel(index: Int, levels: List<Long>): List<Long> {
        return levels.filterIndexed { idx, _ -> idx != index }
    }

    override fun partOne(input: List<String>): Any {
        val levels = input.map { it.split(" ").map(String::toLong) }
        val safeLevels: Long = levels.sumOf {
            if (isValidAdjacentDifference(it) && (isDecreasing(it) || isIncreasing(it))) 1L else 0L
        }
        return safeLevels
    }

    override fun partTwo(input: List<String>): Any {
        val levels = input.map { it.split(" ").map(String::toLong) }

        val safeLevels: Long = levels.sumOf {
            if (isValidAdjacentDifference(it) && (isDecreasing(it) || isIncreasing(it)))
                1L
            else {
                var value: Long
                val check = it.withIndex().any { (index, _) ->
                    val removed = removeLevel(index, it)
                    value = if (isValidAdjacentDifference(removed) && (isDecreasing(removed) || isIncreasing(removed))) 1L else 0L
                    value == 1L
                }
                if (check) 1L else 0L
            }
        }

        return safeLevels
    }
}

fun main() {
    val lines = File("src/main/resources/2024/input_day_02.txt").convertToListString()
    val day = Day02()
    day.solvePart1(lines)
    day.solvePart2(lines)
}