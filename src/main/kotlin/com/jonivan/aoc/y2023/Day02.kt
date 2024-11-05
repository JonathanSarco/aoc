package com.jonivan.aoc.y2023

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils


class Day02 : Solution<List<String>>() {

    class Game {
        companion object {
            fun getGameId(text: String): Int {
                return text.split("Game ")[1].toInt()
            }
        }
    }

    enum class ColorMin(val color: String, val minNumber: Int) {
        RED("red", 12),
        GREEN("green", 13),
        BLUE("blue", 14)
    }

    private fun isValidCubeSet(setOfColors: List<String>): Boolean {
        var isValid = true

        setOfColors.forEach {
            val oneSet = it.split(",").map { v -> v.trimStart() }
            val blueColor =
                oneSet.firstOrNull { s -> s.contains(ColorMin.BLUE.color) }?.split(" ${ColorMin.BLUE.color}")
            if (!blueColor.isNullOrEmpty() && blueColor[0].toInt() > 14) {
                isValid = false
            }

            val greenColor =
                oneSet.firstOrNull { s -> s.contains(ColorMin.GREEN.color) }?.split(" ${ColorMin.GREEN.color}")
            if (!greenColor.isNullOrEmpty() && greenColor[0].toInt() > 13) {
                isValid = false
            }

            val redColor = oneSet.firstOrNull { s -> s.contains(ColorMin.RED.color) }?.split(" ${ColorMin.RED.color}")
            if (!redColor.isNullOrEmpty() && redColor[0].toInt() > 12) {
                isValid = false
            }
        }
        return isValid
    }


    private fun getMinimalParOfSet(setOfColors: List<String>): Int {
        val allSet = setOfColors.flatMap { it.split(",") }.map { v -> v.trimStart() }

        val blue = allSet.filter { it.contains("blue") }.flatMap { v -> v.split(" blue") }
        val b = blue.filterNot { it.isEmpty() }.map { v -> v.toInt() }.max()

        val green = allSet.filter { it.contains("green") }.flatMap { v -> v.split(" green") }
        val g = green.filterNot { it.isEmpty() }.map { v -> v.toInt() }.max()

        val red = allSet.filter { it.contains("red") }.flatMap { v -> v.split(" red") }
        val r = red.filterNot { it.isEmpty() }.map { v -> v.toInt() }.max()

        return b * g * r
    }

    override fun partOne(input: List<String>): Int {
        var totalSum = 0
        input.forEach { it ->
            val result = it.split(":")
            val gameId = Game.getGameId(result[0])
            val sets = result[1].split(";").map { it.trimStart() }
            if (isValidCubeSet(sets)) {
                totalSum += gameId
            }
        }
        return totalSum
    }

    override fun partTwo(input: List<String>): Int {
        var totalSum = 0
        input.forEach { it ->
            val result = it.split(":")
            val sets = result[1].split(";").map { it.trimStart() }
            totalSum += getMinimalParOfSet(sets)
        }
        return totalSum
    }
}


fun main() {
    val lines = InputUtils.readFileAsListString("src/main/resources/2023/input_day_02.txt")
    val day = Day02()
    day.solvePart1(lines)
    day.solvePart2(lines)
}
