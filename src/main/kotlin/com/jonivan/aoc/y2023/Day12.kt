package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils
import kotlin.math.pow
import kotlin.time.measureTime

val regex = """(#+)""".toRegex()

private data class ConditionRecord(val ground: String, val numbers: List<Int>)

private fun getUnknownParts(record: ConditionRecord): List<Int> {
    return record.ground.withIndex().filter { (_, value) -> value == '?' }.map { (index, _) -> index }
}

private fun isValidCombination(
    record: ConditionRecord,
    unknownIdx: List<Int>,
    numberCombination: Int,
): Boolean {
    val binaryNumber = numberCombination.toString(2).padStart(unknownIdx.size, '0')

    val result = buildString {
        append(record.ground)
        unknownIdx.forEachIndexed { indexUnknown, unknownNumberIndex ->
            val current = binaryNumber[indexUnknown]
            replace(unknownNumberIndex, unknownNumberIndex + 1, if (current == '1') "." else "#")
        }
    }

    val group = regex.findAll(result)

    val g = group.map { it.groups[0]!!.value.length }.toList()

    return g == record.numbers
}


private fun partOne(input: List<String>) {
    val records = input.map {
        val ground = it.substringBefore(" ")
        val numbers = it.substringAfter(" ").split(",").map { status -> status.toInt() }
        ConditionRecord(ground, numbers)
    }

    val result = records.sumOf { rec ->
        val unknownPartsIdx = getUnknownParts(rec)

        val amountOfCombinations = 2.0.pow(unknownPartsIdx.size.toDouble()).toInt()

        val validCombinations = (0..<amountOfCombinations).count {
            isValidCombination(rec, unknownPartsIdx, it)
        }

        println("combinations $validCombinations")
        validCombinations
    }

    println("P1: Result: $result")
}

private fun partTwo(input: List<String>) {
    val records = input.map {
        val ground = it.substringBefore(" ")
        val numbers = it.substringAfter(" ").split(",").map { status -> status.toInt() }
        ConditionRecord(ground, numbers)
    }

    val recordsExpanded: List<ConditionRecord> = records.map { rec ->
        val groundReplaced = (rec.ground + "?").repeat(5).removeSuffix("?")
        val som: MutableList<List<Int>> = mutableListOf()
        repeat(5) { som.add(records[0].numbers) }

        ConditionRecord(groundReplaced, som.flatten())
    }

    println("records expanded")
    println(recordsExpanded)


    val result = recordsExpanded.sumOf { rec ->
        val unknownPartsIdx = getUnknownParts(rec)

        val amountOfCombinations = 2.0.pow(unknownPartsIdx.size.toDouble()).toInt()

        val validCombinations = (0..<amountOfCombinations).count {
            isValidCombination(rec, unknownPartsIdx, it)
        }

        println("combinations $validCombinations")
        validCombinations
    }

    println("P2 result: $result")
}

fun main() {
    val input = InputUtils.readFileAsList("src/main/resources/2023/input_day_12.txt")
    println("P1 time:  ${measureTime { partOne(input) }}")
    println("P2 time:  ${measureTime { partTwo(input) }}")
}