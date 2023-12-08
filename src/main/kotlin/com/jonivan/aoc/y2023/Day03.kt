package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils


data class NumberPos(val value: Int, val startPosCoord: Pair<Int, Int>) {
    val bordersWithNumberPos = mutableListOf<Pair<Int, Int>>();
    private val length = value.toString().length;
    init {
        bordersWithNumberPos.add(startPosCoord.copy(second = startPosCoord.second - 1))
        bordersWithNumberPos.add(startPosCoord.copy(second = startPosCoord.second + length))

        IntRange(startPosCoord.second - 1, startPosCoord.second + length)
            .forEach { x ->
                bordersWithNumberPos.add(startPosCoord.first - 1 to x)
                bordersWithNumberPos.add(startPosCoord.first + 1 to x)
            }
        println("border $bordersWithNumberPos")
    }
}

private fun partOne(input: List<String>) {
    val numRegex = Regex("\\d+")
    val allSymbolsRegex = Regex("[!@#$%^&*()+\\-=_/<>,`~|]")

    val possibleParts = mutableListOf<NumberPos>()
    val symbolsMap = mutableMapOf<Pair<Int, Int>, String>()

    input.forEachIndexed { lineNum, line ->

        possibleParts.addAll(numRegex.findAll(line).map {
            NumberPos(it.value.toInt(), lineNum to it.range.first)
        })

        allSymbolsRegex.findAll(line).forEach {
            symbolsMap[lineNum to it.range.first] = it.value
        }
    }

    val validParts = possibleParts.filter { part ->
        part.bordersWithNumberPos.any { c -> symbolsMap.containsKey(c)}
    }

    println("result: ${validParts.sumOf { it.value }}")
}


private fun partTwo(input: List<String>) {
    val numRegex = Regex("\\d+")
    val allSymbolsRegex = Regex("[*]")

    val possibleParts = mutableListOf<NumberPos>()
    val symbolsMap = mutableMapOf<Pair<Int, Int>, String>()

    input.forEachIndexed { lineNum, line ->
        possibleParts.addAll(numRegex.findAll(line).map {
            NumberPos(it.value.toInt(), lineNum to it.range.first)
        })

        allSymbolsRegex.findAll(line).forEach {
            symbolsMap[lineNum to it.range.first] = it.value
        }
    }

    val gears: MutableList<Int> = mutableListOf()

    symbolsMap.forEach { (key, _) ->
        run {
            val r = possibleParts.filter { part -> part.bordersWithNumberPos.any { c -> key == c }}
            if (r.size > 1) {
                gears.add(r[0].value * r[1].value)
            }
        }
    }
    println("result: ${gears.sumOf { it }}")
}

fun main() {
    val input = InputUtils.readFileAsList("src/main/resources/2023/input_day_03.txt")
    partOne(input)
    partTwo(input)
}
