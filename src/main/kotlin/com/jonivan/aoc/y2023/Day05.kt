package com.jonivan.aoc.y2023

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils
import java.util.stream.LongStream


class Day05 : Solution<List<String>, Int>() {

    data class Range(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long)

    data class Map(val key: String, val values: List<Range>) {
        fun get(key: Long): Long {
            for (entry in values) {
                val rangeToCalculate = entry.sourceRangeStart
                val finishRange = entry.sourceRangeStart + entry.rangeLength - 1

                if (key in rangeToCalculate..finishRange) {
                    val distance = key - rangeToCalculate
                    return entry.destinationRangeStart + distance
                }
            }
            return key
        }

        fun getKey(value: Long): Long {
            for (entry in values) {
                val destinationLow: Long = entry.destinationRangeStart
                val destinationHigh: Long = destinationLow + entry.rangeLength - 1
                if (value in destinationLow..destinationHigh) {
                    val distance = value - destinationLow
                    return entry.sourceRangeStart + distance
                }
            }
            return value
        }

        fun getAllValues(): LongStream {
            val highestDestination = values.maxOfOrNull { it.destinationRangeStart + it.rangeLength }
            return LongStream.range(0, highestDestination ?: 0).sorted()
        }
    }

    private fun mapInputToAlmanac(input: List<String>): List<List<String>> {
        val components: MutableList<List<String>> = mutableListOf()
        val currentComponent: MutableList<String> = mutableListOf()
        input.forEach { line ->
            if (line.isBlank()) {
                if (currentComponent.isNotEmpty()) {
                    components.add(ArrayList(currentComponent))
                    currentComponent.clear()
                }
            } else {
                currentComponent.add(line)
            }
        }
        components.add(ArrayList(currentComponent))
        return components
    }

    private fun getSeeds(almanac: List<String>): List<Long> {
        val seeds = almanac[0].split(":")[1].split(" ").filter { it.isNotEmpty() }
        return seeds.map { it.toLong() }
    }

    private fun getKey(input: String): String {
        return input.split(" ")[0]
    }

    private fun getValues(inputs: List<String>): List<Range> {
        val result: MutableList<Range> = mutableListOf()

        inputs.forEach { valList ->
            val numbers = valList.split(" ").map { it.toLong() }
            result.add(Range(numbers[0], numbers[1], numbers[2]))
        }

        return result
    }

    private fun getMaps(almanacWithoutSeeds: List<List<String>>): MutableList<Map> {
        val resultMap: MutableList<Map> = mutableListOf()

        almanacWithoutSeeds.forEach { valueMap ->
            val key = getKey(valueMap.first())
            val values = getValues(valueMap)
            resultMap.add(Map(key, values))
        }

        return resultMap
    }

    override fun partOne(input: List<String>): Int {
        val almanac = mapInputToAlmanac(input)
        val seeds = getSeeds(almanac.first())
        val maps = getMaps(almanac)

        val minimum = seeds.minOfOrNull { seed: Long ->
            var value = seed
            for (map in maps) {
                value = map.get(value) // get until the end of the map
            }
            value
        }

        return minimum?.toInt() ?: 0
    }

    private fun isSeedBetweenValues(ranges: List<List<Long>>, seed: Long): Boolean {
        for (range in ranges) {
            if (range[0] <= seed && seed < range[0] + range[1]) {
                return true
            }
        }
        return false
    }

    override fun partTwo(input: List<String>): Int {
        val almanac = mapInputToAlmanac(input)
        val seeds = getSeeds(almanac.first())
        val maps = getMaps(almanac).reversed()

        val seedsWithRange = seeds.chunked(2)

        val locations = maps[0].getAllValues()

        val iterator = locations.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            var value = next
            for (map in maps) {
                value = map.getKey(value)
            }
            if (isSeedBetweenValues(seedsWithRange, value)) {
                return next.toInt()
            }
        }
        return 0
    }
}

fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_05.txt")
    val day = Day05()
    day.solvePart1(input)
    day.solvePart2(input)
}
