package com.jonivan.aoc.utils

import java.io.File

class InputUtils {
    companion object {
        fun readFileAsListString(filename: String): List<String> {
            val response: MutableList<String> = mutableListOf()
            File(filename).useLines { lines ->
                lines.forEach {
                    response.add(it)
                }
            }
            return response
        }

        fun readFileAsString(filename: String): String = File(filename).readText(Charsets.UTF_8)

        fun File.convertToListString(): List<String> = this.readLines().dropLastWhile { it.isBlank() }
    }
}
