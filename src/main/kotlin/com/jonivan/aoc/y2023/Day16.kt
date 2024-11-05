package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils

private enum class Direction(val value: String) {
    UP("^"),
    DOWN("v"),
    LEFT("<"),
    RIGHT(">"),
}

private data class CurrentState(var row: Int, var col: Int, var direction: Direction, var visited: MutableSet<String>)

private fun isOutOfBounds(matrix: List<List<String>>, row: Int, col: Int): Boolean {
    return row < 0 || col < 0 || row >= matrix.size || col >= matrix[0].size
}

private fun moveToDirection(state: CurrentState): CurrentState {
    val (row, col, direction, _) = state
    return when (direction) {
        Direction.UP -> state.copy(row = row - 1)
        Direction.DOWN -> state.copy(row = row + 1)
        Direction.LEFT -> state.copy(col = col - 1)
        Direction.RIGHT -> state.copy(col = col + 1)
    }
}

fun render(grid: List<List<String>>, energized: Set<String>) {
    for (x in grid.indices) {
        var row = ""
        for (y in 0 ..<grid[0].size) {
            row += if ("$x,$y" in energized) "#" else grid[y][x]
        }
    }
}
private fun resolveBothParts(matrix: List<List<String>>) {
    val energized: MutableSet<String> = mutableSetOf()

    tailrec fun  step(state: CurrentState) {
        if (isOutOfBounds(matrix, state.row, state.col)) return

        val (row, col, direction, visited) = state

        val key = "$row,$col,$direction"

        if (visited.contains(key)) return
        visited.add(key)

        energized.add("$row,$col")

        when (matrix[row][col]) {
            "." -> step(moveToDirection(state))
            "|" -> when (direction) {
                Direction.UP, Direction.DOWN -> step(moveToDirection(state))
                Direction.LEFT, Direction.RIGHT -> {
                    step(moveToDirection(CurrentState(row, col, Direction.UP, visited)))
                    step(moveToDirection(CurrentState(row, col, Direction.DOWN, visited)))
                }
            }
            "-" -> when (direction) {
                Direction.LEFT, Direction.RIGHT -> step(moveToDirection(state))
                Direction.UP, Direction.DOWN -> {
                    step(moveToDirection(CurrentState(row,col, Direction.LEFT, visited)))
                    step(moveToDirection(CurrentState(row,col, Direction.RIGHT, visited)))
                }
            }
            "/" -> when (direction) {
                Direction.UP -> step(moveToDirection(CurrentState(row,col, Direction.RIGHT, visited)))
                Direction.DOWN -> step(moveToDirection(CurrentState(row,col, Direction.LEFT, visited)))
                Direction.LEFT -> step(moveToDirection(CurrentState(row,col, Direction.DOWN, visited)))
                Direction.RIGHT -> step(moveToDirection(CurrentState(row,col, Direction.UP, visited)))
            }
            "\\" -> when (direction) {
                Direction.UP -> step(moveToDirection(CurrentState(row,col, Direction.LEFT, visited)))
                Direction.DOWN -> step(moveToDirection(CurrentState(row,col, Direction.RIGHT, visited)))
                Direction.LEFT -> step(moveToDirection(CurrentState(row,col, Direction.UP, visited)))
                Direction.RIGHT -> step(moveToDirection(CurrentState(row,col, Direction.DOWN, visited)))
            }
        }
    }

    // Part1
    step(CurrentState(0, 0, Direction.RIGHT, mutableSetOf()))
    render(matrix, energized)
    println("P1 result: ${energized.size}")


    val total = mutableSetOf<Int>()

    for (i in matrix.indices) {
        step(CurrentState(i, 0, Direction.RIGHT, mutableSetOf()))
        render(matrix, energized)
        total.add(energized.size)
        energized.clear()

        step(CurrentState(i, matrix.size - 1, Direction.LEFT, mutableSetOf()))
        render(matrix, energized)
        total.add(energized.size)
        energized.clear()

        step(CurrentState(0, i, Direction.DOWN, mutableSetOf()))
        render(matrix, energized)
        total.add(energized.size)
        energized.clear()

        step(CurrentState(matrix.size - 1, i, Direction.UP, mutableSetOf()))
        render(matrix, energized)
        total.add(energized.size)
        energized.clear()
    }
    println("P2 result: ${total.max()}")
}


fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_16.txt")
    resolveBothParts(input.map { row -> row.map { it.toString() } })
}