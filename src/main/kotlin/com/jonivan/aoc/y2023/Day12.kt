package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils.Companion.convertToListString
import com.jonivan.aoc.utils.dropBlanks
import java.io.File
import kotlin.time.measureTime

private val cache = hashMapOf<Pair<String, List<Int>>, Long>()

private fun count(config: String, groups: List<Int>): Long {
    if (groups.isEmpty()) return if ("#" in config) 0 else 1
    if (config.isEmpty()) return 0

    return cache.getOrPut(config to groups) {
        var result = 0L
        if (config.first() in ".?") {
            result += count(config.drop(1), groups)
        }
        if (config.first() in "#?" && groups.first() <= config.length && "." !in config.take(groups.first()) && (groups.first() == config.length || config[groups.first()] != '#')) {
            result += count(config.drop(groups.first() + 1), groups.drop(1))
        }
        result
    }
}

private fun partOne(input: List<String>) {
    val result = input.sumOf { it.split(" ").let { count(it.first(), it[1].split(",").map(String::toInt)) } }

    println("P1: Result: $result")
}

private fun partTwo(input: List<String>) {
    val response =
        input.sumOf {
            it.split(" ").let { character ->
                count(
                    "${character.first()}?".repeat(5).dropLast(1),
                    "${character[1]},".repeat(5).split(",").dropBlanks().map(String::toInt),
                )
            }
        }

    println("P2: Result: $response")
}

fun main() {
    val input = File("src/main/resources/2023/input_day_12.txt").convertToListString()
    println("P1 time:  ${measureTime { partOne(input) }}")
    println("P2 time:  ${measureTime { partTwo(input) }}")
}
