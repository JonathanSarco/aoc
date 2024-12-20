package com.jonivan.aoc.y2023

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils


class Day06: Solution<List<String>>() {
    private fun getParts(input: List<String>, index: Int = 0): List<Long> {
        val part = input[index].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        println("index[$index] -> $part")
        return part
    }

    private fun getPartsCombined(input: List<String>, index: Int = 0): Long {
        val part = input[index].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        println("index[$index] -> $part")
        return part.joinToString("").toLong()
    }



    private fun calculateSpeed(maxTimeRace: Long, recordDistance: Long): Int {
        var totalChanges = 0
        for (i in 1..<maxTimeRace) { // holding 1 until max minus 1
            val totalDistance = (maxTimeRace - i) * i
            if (totalDistance > recordDistance) {
                totalChanges++
            }
        }
        return totalChanges
    }


    override fun partOne(input: List<String>): Int {
        val times = getParts(input, 0)
        val distance = getParts(input, 1)
        var totalChances = 1

        for (i in times.indices) {
            val chancesPerRace = calculateSpeed(times[i], distance[i])
            totalChances *= chancesPerRace
        }
        return totalChances
    }

    override fun partTwo(input: List<String>): Int {
        val times = getParts(input, 0)
        val distance = getParts(input, 1)
        var totalChances = 1

        for (i in times.indices) {
            val chancesPerRace = calculateSpeed(times[i], distance[i])
            totalChances *= chancesPerRace
        }
        return totalChances
    }

}

fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_06.txt")
    val day = Day06()
    day.solvePart1(input)
    day.solvePart2(input)
}