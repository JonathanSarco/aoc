package com.jonivan.aoc.utils

import com.jonivan.aoc.y2021.Cell

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
    }
}