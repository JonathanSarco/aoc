package com.jonivan.aoc.utils

import java.io.File

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
    }
}
