package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils
import java.lang.Math.abs

private data class Location(val row: Int, val col: Int)

private fun findStartingPoint(list: List<List<String>>, value: String): Location? {
    for (i in list.indices) {
        for (j in list[i].indices) {
            if (list[i][j] == value) {
                return Location(i, j)
            }
        }
    }
    return null
}

private operator fun Location.plus(other: Location): Location = Location(row + other.row, col + other.col)

private enum class Cardinal {
    NORTH, EAST, SOUTH, WEST
}

private fun Cardinal.toLocation(): Location = when (this) {
    Cardinal.NORTH -> Location(-1, 0)
    Cardinal.EAST -> Location(0, 1)
    Cardinal.SOUTH -> Location(1, 0)
    Cardinal.WEST -> Location(0, -1)
}

private fun Cardinal.opposite(): Cardinal = when (this) {
    Cardinal.NORTH -> Cardinal.SOUTH
    Cardinal.EAST -> Cardinal.WEST
    Cardinal.SOUTH -> Cardinal.NORTH
    Cardinal.WEST -> Cardinal.EAST
}

private fun gridPositionToNESW(matrix: List<List<String>>, pos: Location): List<Cardinal> =
    when (matrix[pos.row][pos.col]) {
        "|" -> listOf(Cardinal.NORTH, Cardinal.SOUTH)
        "-" -> listOf(Cardinal.EAST, Cardinal.WEST)
        "L" -> listOf(Cardinal.NORTH, Cardinal.EAST)
        "J" -> listOf(Cardinal.NORTH, Cardinal.WEST)
        "7" -> listOf(Cardinal.SOUTH, Cardinal.WEST)
        "F" -> listOf(Cardinal.SOUTH, Cardinal.EAST)
        "S" -> listOf(Cardinal.NORTH, Cardinal.SOUTH, Cardinal.EAST, Cardinal.WEST)
        "." -> listOf()
        else -> listOf()
    }

private fun getNext(matrix: List<List<String>>, location: Location, dir: Cardinal): Pair<Location, Cardinal>? {
    val nextPos = location + dir.toLocation()
    val nextDirs = gridPositionToNESW(matrix, nextPos)
    if (dir.opposite() !in nextDirs) return null
    return nextPos to nextDirs.minus(dir.opposite()).first()
}

private fun partOne(input: List<String>) {
    val matrix = input.map { r -> r.map { c -> c.toString()  } }

    val startingPoint = findStartingPoint(matrix, "S")

    val tmpLoop = mutableListOf<Location>()

    for (startDir in Cardinal.entries) {
        tmpLoop.clear()
        tmpLoop.add(startingPoint!!)
        val start = getNext(matrix, startingPoint, startDir) ?: continue
        var curPos = start.first
        var curDir = start.second

        while (matrix[curPos.row][curPos.col] != "S") {
            tmpLoop.add(Location(curPos.row, curPos.col))
            val (nextPos, nextDir) = getNext(matrix, Location(curPos.row, curPos.col), curDir) ?: break
            curPos = nextPos
            curDir = nextDir
        }
        if (matrix[curPos.row][curPos.col] == "S") break
    }

    println( "${(tmpLoop.size / 2)}")
}

private fun part2(input: List<String>) {
    val matrix = input.map { r -> r.map { c -> c.toString()  } }
    val startingPoint = findStartingPoint(matrix, "S")
    val tmpLoop = mutableListOf<Location>()

    for (startDir in Cardinal.entries) {
        tmpLoop.clear()
        tmpLoop.add(startingPoint!!)
        val start = getNext(matrix, startingPoint, startDir) ?: continue
        var curPos = start.first
        var curDir = start.second

        while (matrix[curPos.row][curPos.col] != "S") {
            tmpLoop.add(Location(curPos.row, curPos.col))
            val (nextPos, nextDir) = getNext(matrix, Location(curPos.row, curPos.col), curDir) ?: break
            curPos = nextPos
            curDir = nextDir
        }
        if (matrix[curPos.row][curPos.col] == "S") break
    }

    val p2 = (1 ..< matrix.size - 1).sumOf { x ->
        val idx = matrix[x].indices.filter { y ->
            val i1 = tmpLoop.indexOf(Location(x, y))
            val i2 = tmpLoop.indexOf(Location(x + 1, y))
            i1 != -1 && i2 != -1 && (abs(i1 - i2) == 1 || i1 in listOf(0, tmpLoop.lastIndex) && i2 in listOf(0, tmpLoop.lastIndex))
        }
        (idx.indices step 2).sumOf { i ->
            (idx[i] .. idx[i + 1]).count { y -> Location(x ,y) !in tmpLoop }
        }
    }.toString()

    println("Part 2: $p2")
}



fun main() {
    val input = InputUtils.readFileAsList("src/main/resources/2023/input_day_10.txt")
    partOne(input)
    part2(input)
}