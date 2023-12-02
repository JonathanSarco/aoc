package com.jonivan.aoc.y2021

import com.jonivan.aoc.utils.ListsUtils
import com.jonivan.aoc.utils.StringUtils
import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int)

fun readFileAndMap(): List<Pair<Coordinate, Coordinate>> {
    val readFile = ListsUtils.readFile("src/main/resources/input_day_05.txt")
    return StringUtils.stringToCoordinates(readFile)
}

fun isHorizontal(line: Pair<Coordinate, Coordinate>) = line.first.x == line.second.x
fun isVertical(line: Pair<Coordinate, Coordinate>): Boolean = line.first.y == line.second.y

fun isDiagonal(line: Pair<Coordinate, Coordinate>): Boolean {
    if (abs(line.first.x - line.second.x) == abs(line.first.y - line.second.y)) {
        return true
    }
    return false
}

enum class POSITION {
    X, Y, DG_MIN, DG_MAX
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
        POSITION.DG_MIN ->
            if (line.first.x > line.second.x) {
                Pair(line.first.x, line.first.y)
            } else {
                Pair(line.second.x, line.second.y)
            }
        POSITION.DG_MAX -> if (line.first.x < line.second.x) {
            Pair(line.first.x, line.first.y)
        } else {
            Pair(line.second.x, line.second.y)
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
        println("each: $it")
        if (isDiagonal(it)) {
            println("diagonal: $it")
            var min = getMayorMinorFromCoordinates(POSITION.DG_MAX, it)
            val max = getMayorMinorFromCoordinates(POSITION.DG_MIN, it)

            println("min: $min, max $max")

            var steps = abs(min.first - max.first);
            val xStep = if (min.first > max.first) -1 else 1;
            val yStep = if (min.second > max.second) -1 else 1;
            coverPoint(board, min.first, min.second)
            var x = min.first
            var y = min.second
            while (steps > 0) {
                steps--
                x += xStep;
                y += yStep;
                coverPoint(board, x, y)
            }
        }
        if (isHorizontal(it)) {
            println("horizontal: $it")
            val c = getMayorMinorFromCoordinates(POSITION.X, it)
            val xValue = it.first.x
            for (i in c.first..c.second) {
                coverPoint(board, xValue, i)
            }
        }
        if (isVertical(it)) {
            println("vertical: $it")
            val c = getMayorMinorFromCoordinates(POSITION.Y, it)
            val yValue = it.first.y
            for (i in c.first..c.second) {
                coverPoint(board, i, yValue)
            }
        }
    }

    var sum = 0
    board.forEach {
        if (it.value > 1) {
            sum++
        }
    }
    println("Total overlap $sum")
}

