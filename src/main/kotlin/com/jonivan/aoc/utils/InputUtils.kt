package com.jonivan.aoc.utils

import java.io.File

class InputUtils {
    companion object {
        fun readFileAsList(filename: String): List<String> {
            val response: MutableList<String> = mutableListOf()
            File(filename).useLines { lines ->
                lines.forEach {
                    response.add(it)
                }
            }
            return response
        }

        fun readFileAsString(filename: String): String {
            return File(filename).readText(Charsets.UTF_8)
        }
    }
}
