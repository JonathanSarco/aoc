package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils
import kotlin.time.measureTime

private fun isAllZeros(differences: List<Int>): Boolean {
    return differences.all { it == 0 }
}

private fun calculatePredictionPast(diffs: List<MutableList<Int>>): Int {
    val diffBottom = diffs.reversed()

    diffBottom.reversed().forEach { println(it) }

    diffBottom[0].add(0)

    diffBottom.zipWithNext { a, b ->
        val lastAdded = a.first()
        val previousLast = b.first()
        b.add(previousLast - lastAdded)
    }

    diffBottom.reversed().forEach { println(it) }

    // Get last value added
    return diffBottom.last()[0]
}

private fun calculatePredictionFuture(diffs: List<MutableList<Int>>): Int {
    val diffBottom = diffs.reversed()
    diffBottom[0].add(0)

    diffBottom.zipWithNext { a, b ->
        val lastAdded = a.last()
        val previousLast = b.last()
        b.add(lastAdded + previousLast)
    }

    // Get last value added
    return diffBottom.last()[diffBottom.last().size - 1]
}

private fun getDifferences(distances: List<Int>): List<MutableList<Int>> {
    val newDistances: MutableList<MutableList<Int>> = mutableListOf(distances.toMutableList())
    var current = distances

    while (!isAllZeros(current)) {
        val diffs = current.zipWithNext { a, b -> b - a }.toMutableList()
        newDistances.add(diffs)
        current = diffs
    }
    return newDistances
}

private fun partOne(input: List<String>) {
    val distances = input.map { dis ->
        dis.split(" ").map { it.trim().toInt() }
    }

    val allDifferences = distances.map { getDifferences(it) }

    val result = allDifferences.sumOf { calculatePredictionFuture(it) }
    println("P1 result: $result")
}

private fun partTwo(input: List<String>) {
    val distances = input.map { dis ->
        dis.split(" ").map { it.trim().toInt() }
    }

    val allDifferences = distances.map { getDifferences(it) }

    val result = allDifferences.sumOf { calculatePredictionPast(it) }
    println("P2 result: $result")
}

fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_09.txt")
    println("P1 time:  ${measureTime { partOne(input) }}")
    println("P2 time:  ${measureTime { partTwo(input) }}")
}
