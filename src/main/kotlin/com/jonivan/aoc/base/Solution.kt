package com.jonivan.aoc.base

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.TimeUnit

abstract class Solution<T> {

    private val logger = KotlinLogging.logger {}

    abstract fun partOne(input: T): Any

    fun solvePart1(input: T) {
        val initTime = System.nanoTime()
        val solution = partOne(input)
        val finishTime = System.nanoTime()
        val totalTime: Long = TimeUnit.NANOSECONDS.toMillis(finishTime - initTime)

        logger.info { "Part1: $solution - TotalTime: $totalTime" }
    }

    abstract fun partTwo(input: T): Any

    fun solvePart2(input: T) {
        val initTime = System.nanoTime()
        val solution = partOne(input)
        val finishTime = System.nanoTime()
        val totalTime: Long = TimeUnit.NANOSECONDS.toMillis(finishTime - initTime)

        logger.info { "Part2: $solution - TotalTime: $totalTime" }
    }
}