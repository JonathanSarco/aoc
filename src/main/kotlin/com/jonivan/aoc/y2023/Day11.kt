package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils
import kotlin.math.abs
private data class Point(val row: Int = -1, val col: Int = -1) // values to avoid iteration
private fun isExpandable(galaxy: List<String>): Boolean {
    return galaxy.all { it == "." }
}
private fun getGalaxiesToExpand(matrix: List<List<String>>): List<Point> {
    val toExpand: MutableList<Point> = mutableListOf()
    matrix.forEachIndexed { row, values ->
        if (isExpandable(values)) {
            toExpand.add(Point(row = row))
        }
    }
    // matrix[0] = ...#......
    for (col in 0..<matrix[0].size) {
        val valuesColumn = matrix.map { it[col] }
        if (isExpandable(valuesColumn)) {
            toExpand.add(Point(col = col))
        }
    }
    return toExpand.toList()
}
private fun expandUniverse(expanded: MutableList<MutableList<String>>, expansion: Int = 1_000_000): List<List<String>> {
    val galaxiesToExpand = getGalaxiesToExpand(expanded)
    val additional = expansion - 1
    var sy = 0
    galaxiesToExpand.forEach { point ->
        if (point.row != -1) {
            sy += additional
            expanded.add(point.row + sy, MutableList(expanded[0].size) { "." })
        }
    }
    var sx = 0
    galaxiesToExpand.forEach { point ->
        if (point.col != -1) {
            for (row in 0..<expanded.size) {
                expanded[row].add(point.col + sx, ".")
            }
            sx += additional
        }
    }
    return expanded
}
private fun findGalaxies(matrix: List<List<String>>): MutableList<Point> {
    val gPositions: MutableList<Point> = mutableListOf()
    matrix.forEachIndexed { indexRow, row ->
        row.forEachIndexed { indexCol, col ->
            if (col == "#") {
                gPositions.add(Point(indexRow, indexCol))
            }
        }
    }
    return gPositions
}
private fun calculateDistance(p1: Point, p2: Point): Int {
    return abs(p1.row - p2.row) + abs(p1.col - p2.col)
}
private fun partOne(input: List<String>) {
    val matrix = input.map { line -> line.map { it.toString() } }
    val universeExpanded = expandUniverse(matrix.map { it.toMutableList() }.toMutableList())
    println("\n Expanded")
    var count = 0
    universeExpanded.forEachIndexed { _, o ->
        println(
            "> ${o.map { x ->
                if (x == "#"){
                    count++
                    (count).toString()
                } else {
                    "."
                }
            }}",
        )
    }
    val galaxies = findGalaxies(universeExpanded)
    galaxies.forEach {
        println("G: $it")
    }
    var total = 0
    while (galaxies.size > 0) {
        val first = galaxies.removeFirst()
        galaxies.forEach { p1 ->
            total += calculateDistance(first, p1)
        }
    }
    println("total $total")
}
fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_11.txt")
    partOne(input)
    // partOne(input)
}
