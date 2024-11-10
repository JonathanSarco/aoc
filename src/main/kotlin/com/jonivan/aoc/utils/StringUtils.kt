package com.jonivan.aoc.utils

import com.jonivan.aoc.y2021.Cell
import com.jonivan.aoc.y2021.Coordinate

class StringUtils {

    companion object {
        fun stringToMatrices(input: String): List<List<List<Cell>>> {
            val matrixStrings = input.trim().split("\n\n")

            return matrixStrings.map { matrixString ->
                matrixString.trim().split("\n").map { rowString ->
                    rowString.trim().split(" ").filter { it != "" }.map { Cell(it.toInt()) }
                }
            }
        }

        fun stringToCoordinates(input: List<String>): List<Pair<Coordinate, Coordinate>> {
            val result = input.map {
                val result = it.split(" -> ")
                val f1 = result[0].split(",")
                val left = Coordinate(f1[0].toInt(), f1[1].toInt())

                val f2 = result[1].split(",")
                val right = Coordinate(f2[0].toInt(), f2[1].toInt())

                Pair(left, right)
            }
            return result
        }
    }
}
