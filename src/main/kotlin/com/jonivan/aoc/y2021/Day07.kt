package com.jonivan.aoc.y2021

import com.jonivan.aoc.utils.ListsUtils

fun readCrabs(): MutableList<Int> {
    val fishes = ListsUtils.readFile("src/main/resources/input_day_07_01.txt").map { it.split(",") }[0]
    return fishes.map { it.toInt() }.toMutableList()
}

class Crab(
    var value: Int,
    var position: Int
)

fun minPosition(crabs: List<Crab>): Pair<Int, Int> {
    var minFuelUsed = -99
    var minPosition = -1
    crabs.forEach {
        if (minFuelUsed != 0) {
            println("position ${it.position} value ${it.value}")
            minPosition = it.position
            minFuelUsed = if (it.value == it.position) {
                0
            } else {
                it.value - it.position
            }
        }
    }
    return Pair(minFuelUsed, minPosition)
}
fun order() {
    val crabs = readCrabs()
    val crabsMapped: MutableList<Crab> = mutableListOf()
    crabs.forEachIndexed { index, i ->
        crabsMapped.add(Crab(i, index))
    }
    println(minPosition(crabsMapped))
}

fun main() {
    order()
}


