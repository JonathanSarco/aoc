package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils


private fun getParts(input: List<String>, index: Int = 0): List<Long> {
    val part = input[index].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
    println("index[$index] -> $part")
    return part
}

private fun getPartsCombined(input: List<String>, index: Int = 0): Long {
    val part = input[index].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    println("index[$index] -> $part")
    return part.joinToString("").toLong()
}



private fun calculateSpeed(maxTimeRace: Long, recordDistance: Long): Int {
    var totalChanges = 0
//    println("time race $maxTimeRace")
//    println("record distance $recordDistance")
    for (i in 1..<maxTimeRace) { // holding 1 until max minus 1
        val totalDistance = (maxTimeRace - i) * i
//        println("distance $totalDistance")
        if (totalDistance > recordDistance) {
            totalChanges++
        }
    }
    return totalChanges
}

private fun partOne(input: List<String>) {
    val times = getParts(input, 0)
    val distance = getParts(input, 1)
    var totalChances = 1

    for (i in times.indices) {
        val chancesPerRace = calculateSpeed(times[i], distance[i])
//        println("total changes $chancesPerRace")
        totalChances *= chancesPerRace
    }

    println("P1 result: $totalChances")
}

private fun partTwo(input: List<String>) {
    val time = getPartsCombined(input, 0)
    val distance = getPartsCombined(input, 1)
    val chancesPerRace = calculateSpeed(time, distance)
    println("total changes $chancesPerRace")

}


fun main() {
    val input = InputUtils.readFileAsList("src/main/resources/2023/input_day_06.txt")
//    partOne(input)
    partTwo(input)
}
