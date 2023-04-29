package com.jonivan.aoc.utils

import com.jonivan.aoc.y2021.Cell
import java.io.File

fun List<List<Cell>>.printMatrix() {
    for (row in this) {
        for (element in row) {
            print("${element.value}-${element.marked} ")
        }
        println()
    }
}

class ListsUtils {
    companion object {
        fun readFile(filename: String): List<String> {
            val response: MutableList<String> = mutableListOf()
            File(filename).useLines { lines ->
                lines.forEach {
                    response.add(it)
                }
            }
            return response
        }

        val mockList: List<String> = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
        )
    }
}
