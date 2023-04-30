package com.jonivan.aoc.y2021

import com.jonivan.aoc.utils.ListsUtils
import com.jonivan.aoc.utils.StringUtils.Companion.stringToMatrices
import java.io.File

val inputList = ListsUtils.readFile("src/main/resources/input_day_04_1.txt").first().split(",")
val inputMatrix = File("src/main/resources/input_day_04_2.txt").readText()
data class Cell(
    val value: Int,
    var marked: Boolean = false,
) {
    fun markCell(number: Int) {
        if (number == value) marked = true
    }
}

data class Board(var board: List<List<Cell>>) {
    private val matrix: List<List<Cell>> = board

    private fun hasRow(): Boolean {
        return (0..4).any { row ->
            (0..4).all { column ->
                matrix[row][column].marked
            }
        }
    }

    private fun hasColumn(): Boolean {
        return (0..4).any { column ->
            (0..4).all { row ->
                matrix[row][column].marked
            }
        }
    }

    fun sumUnmarked(): Int {
        return matrix.flatten().filter { entry -> !entry.marked }.sumOf { entry -> entry.value }
    }

    fun isWinner() = hasRow() || hasColumn()

    fun markBoardCell(number: Int) {
        matrix.flatten().forEach { entry -> entry.markCell(number) }
    }
}

fun getWinners() {
    val result = stringToMatrices(inputMatrix).map { Board(it) }
    val winners = mutableListOf<Pair<Int, Board>>()

    for (number in inputList) {
        result.forEach { board ->
            if (!board.isWinner()) {
                board.markBoardCell(number.toInt())

                if (board.isWinner()) {
                    winners += number.toInt() to board
                }
            }
        }
    }
    val (number, board) = winners.first()
    val (lastNumber, lastBoard) = winners.last()
    println("Number: ${board.sumUnmarked() * number}")
    println("Last Number: ${lastBoard.sumUnmarked() * lastNumber}")
}

fun main() {
    getWinners()
}
