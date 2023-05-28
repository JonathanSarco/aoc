package com.jonivan.aoc.y2021

import com.jonivan.aoc.utils.ListsUtils
import com.jonivan.aoc.utils.StringUtils

data class Coordinate(val x: Int, val y: Int)

fun readFileAndMap(): List<Pair<Coordinate, Coordinate>> {
    val readFile = ListsUtils.readFile("src/main/resources/input_day_05_02.txt")
    return StringUtils.stringToCoordinates(readFile)
}

fun isHorizontal(line: Pair<Coordinate, Coordinate>) = line.first.x == line.second.x
fun isVertical(line: Pair<Coordinate, Coordinate>): Boolean = line.first.y == line.second.y

enum class POSITION {
    X, Y
}
fun getMayorMinorFromCoordinates(position: POSITION, line: Pair<Coordinate, Coordinate>): Pair<Int,Int> {
    return when (position) {
        POSITION.X -> if (line.first.y < line.second.y) {
            Pair(line.first.y, line.second.y)
        } else {
            Pair(line.second.y, line.first.y)
        }
        POSITION.Y -> if (line.first.x < line.second.x) {
            Pair(line.first.x, line.second.x)
        } else {
            Pair(line.second.x, line.first.x)
        }
    }
}

fun coverPoint(board: MutableMap<Pair<Int,Int>, Int>, xValue: Int, yValue: Int) {
        if (board.containsKey(Pair(xValue, yValue))) {
            val prev = board[Pair(xValue, yValue)]
            if (prev != null) {
                board[Pair(xValue, yValue)] = prev + 1
            }
        } else {
            board[Pair(xValue, yValue)] = 1
        }
}

fun main() {
    val board = mutableMapOf<Pair<Int,Int>, Int>()

    val response = readFileAndMap()
    response.forEach {
        if (isHorizontal(it)) {
            val c = getMayorMinorFromCoordinates(POSITION.X, it)
            val xValue = it.first.x
            for (i in c.first..c.second) {
                coverPoint(board, xValue, i)
            }
        }
        if (isVertical(it)) {
            val c = getMayorMinorFromCoordinates(POSITION.Y, it)
            val yValue = it.first.y
            for (i in c.first..c.second) {
                coverPoint(board, i, yValue)
            }
        }
    }

    var sum = 0
    board.forEach {
        print("x: ${it.key.first}, y: ${it.key.second} => ${it.value}")
        if (it.value > 1) {
            sum++
        }
        println()
    }

    println("Total overlap $sum")
}

