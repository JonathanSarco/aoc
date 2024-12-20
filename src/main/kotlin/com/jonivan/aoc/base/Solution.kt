package com.jonivan.aoc.base

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.TimeUnit

abstract class Solution<T> {

    private val logger = KotlinLogging.logger {}

    private fun solvePart(input: T, partLabel: String, solver: (T) -> Any) {
        val initTime = System.nanoTime()
        val solution = solver(input)
        val finishTime = System.nanoTime()
        val totalTime = TimeUnit.NANOSECONDS.toMillis(finishTime - initTime)

        logger.info { """
            
            -----------------------------------------
                     $partLabel: $solution
                     TotalTime: $totalTime ms
            -----------------------------------------
        """.trimIndent() }
    }

    fun solvePart1(input: T) {
        solvePart(input, "Part1", ::partOne)
    }

    fun solvePart2(input: T) {
        solvePart(input, "Part2", ::partTwo)
    }

    abstract fun partOne(input: T): Any
    abstract fun partTwo(input: T): Any
}