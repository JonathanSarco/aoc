package com.jonivan.aoc.y2021

import com.jonivan.aoc.utils.InputUtils
import java.util.*
import kotlin.collections.ArrayList


class Lanternfish(
    var internalValue: Int,
    var newOne: Boolean
) {
    fun reduceTime() {
        internalValue--
        if (internalValue < 0) {
            internalValue = 6
        }
    }
    fun hasToCreate(): Boolean {
        return if (newOne) {
            if (internalValue == 6) {
                newOne = false
            }
            false
        } else {
            internalValue == 6
        }
    }
}

fun readFishes(): MutableList<Lanternfish> {
    val fishes = InputUtils.readFileAsList("src/main/resources/input_day_06.txt").map { it.split(",") }[0]
    return fishes.map { Lanternfish(it.toInt(), false) }.toMutableList()
}

fun executeTime(days: Int): Int {
    val fishes = readFishes()

    println("initial state")
    fishes.forEach { print("${it.internalValue},") }
    println()

    for (i in 1..days) {
        val newFishes = mutableListOf<Lanternfish>()

        fishes.forEach {
            it.reduceTime()
            if (it.hasToCreate()) {
                newFishes.add(Lanternfish(8, true))
            }
        }
        newFishes.forEach {
            fishes.add(it)
        }
    }
    return fishes.size
}

private fun part2( input: List<String> ): Long {
    val numbers = input[0].split( "," ).map { it.toInt() }

    val lanternfish = Array<Long>( 9 ) { 0 }.toCollection( ArrayList() )

    for ( number in numbers )
    {
        lanternfish[number]++
    }

    for ( i in 1..256 )
    {
        Collections.rotate( lanternfish, -1 )
        lanternfish[6] += lanternfish[8]
    }

    return lanternfish.sum();
}

fun main() {
    executeTime(80)
    val response = part2(InputUtils.readFileAsList("src/main/resources/input_day_06.txt"))
    println(response)
}

