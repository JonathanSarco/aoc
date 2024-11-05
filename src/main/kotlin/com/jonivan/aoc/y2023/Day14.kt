package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils

private data class CellRock(val row: Int, val col: Int)

private fun getBadRocks(matrix: List<List<String>>): MutableList<CellRock> {
    val rocks: MutableList<CellRock> = mutableListOf()

    matrix.forEachIndexed { indexRow, row ->
        row.forEachIndexed { indexCol, col ->
            if (col == "#") {
                rocks.add(CellRock(indexRow, indexCol))
            }
        }
    }
    return rocks
}

private fun replaceRocks(currentRow: List<String>, followingRow: MutableList<String>): MutableList<String> {
    val updatedRow = currentRow.toMutableList()

    currentRow.indices.map { idx ->
        if (currentRow[idx] == "." && followingRow[idx] == "O") {
            updatedRow[idx] = followingRow[idx]
            followingRow[idx] = "."
        }
    }
    return updatedRow
}

private fun calculateBeams(roundedRocksNorth: List<List<String>>): Int {
    return roundedRocksNorth.withIndex().sumOf { rock ->
        val index = rock.index
        val amountRoundedRocks = rock.value.filter { it == "O" }.size
        amountRoundedRocks * (roundedRocksNorth.size - index)
    }
}

private fun turnNorth(matrix: MutableList<MutableList<String>>) {
    for (row in 0..<matrix.size) {
        for (col in 0..<matrix[row].size) {
            if (matrix[row][col] == "O") {
                for (k in row - 1 downTo 0) {
                    if (matrix[k][col] != ".") {
                        break
                    }
                    matrix[k][col] = "O"
                    matrix[k+1][col] = "."
                }
            }
        }
    }
}

fun turnWest(matrix: MutableList<MutableList<String>>) {
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (matrix[i][j] == "O") {
                var k = j - 1
                while (k >= 0 && matrix[i][k] == ".") {
                    matrix[i][k] = "O"
                    matrix[i][k + 1] = "."
                    k--
                }
            }
        }
    }
}

fun turnSouth(matrix: MutableList<MutableList<String>>) {
    for (i in matrix.size - 1 downTo 0) {
        for (j in 0 until matrix[i].size) {
            if (matrix[i][j] == "O") {
                var k = i + 1
                while (k < matrix.size && matrix[k][j] == ".") {
                    matrix[k][j] = "O"
                    matrix[k - 1][j] = "."
                    k++
                }
            }
        }
    }
}

fun turnEast(matrix: MutableList<MutableList<String>>) {
    for (i in matrix.size - 1 downTo 0) {
        for (j in matrix[i].size - 1 downTo 0) {
            if (matrix[i][j] == "O") {
                var k = j + 1
                while (k < matrix[i].size && matrix[i][k] == ".") {
                    matrix[i][k] = "O"
                    matrix[i][k - 1] = "."
                    k++
                }
            }
        }
    }
}

private fun partOne(input: List<List<String>>) {
    val roundedRocksNorth = input.map { it.toMutableList() }.toMutableList()

    turnNorth(roundedRocksNorth)
    println("P1 result: ${calculateBeams(roundedRocksNorth)}")
}

private fun partTwo(input: List<List<String>>) {
    val roundRocks = input.map { it.toMutableList() }.toMutableList()

    val cache = mutableMapOf<String, List<MutableList<String>>>()

    var loops = 0
    var tempPlatform = ""
    var i = 0

    while (i < 1000000000) {
        val str = roundRocks.joinToString("") { it.joinToString("") }
        val test = cache[str]

        if (test != null) {
            if (str == tempPlatform) break
            if (loops <= 0) tempPlatform = str
            roundRocks.clear()
            roundRocks.addAll(test.map { it.toMutableList() })
            loops++
            continue
        }

        turnNorth(roundRocks)
        turnWest(roundRocks)
        turnSouth(roundRocks)
        turnEast(roundRocks)
        cache[str] = roundRocks.map { it.toMutableList() }
        i++
    }

    for (j in 0 until (1000000000 - i) % loops) {
        turnNorth(roundRocks)
        turnWest(roundRocks)
        turnSouth(roundRocks)
        turnEast(roundRocks)
    }

    println("P2 result: ${calculateBeams(roundRocks)}")
}

fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_14.txt")
    partOne(input.map { line -> line.map { it.toString() } })
    partTwo(input.map { line -> line.map { it.toString() } })
}