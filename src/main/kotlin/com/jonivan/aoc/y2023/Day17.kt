package com.jonivan.aoc.y2023

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils



class Day17: Solution<List<String>, Int>() {
    override fun partOne(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    override fun partTwo(input: List<String>): Int {
        TODO("Not yet implemented")
    }

}


private fun partOne(input: List<String>) {
    val heatLostMatrix = input.map { row -> row.map { col -> col.digitToInt() }.toMutableList() } .toMutableList()
}


fun main() {

    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_17.txt")
    partOne(input)
}