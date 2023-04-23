package com.jonivan.aoc.y2021.d3

import com.jonivan.aoc.utils.CountUtils.Companion.binaryToDecimal
import com.jonivan.aoc.utils.CountUtils.Companion.checkCodeNumber
import com.jonivan.aoc.utils.ListsUtils.Companion.readFile
import com.jonivan.aoc.y2021.d3.Occurrences.Companion.getMostOfOccurrences
import com.jonivan.aoc.y2021.d3.Occurrences.Companion.getNumbersBasedOnOccurrences
import java.io.File

/**
 * https://adventofcode.com/2021/day/3
 * part 2
 */
class Occurrences {
    companion object {
        fun getMostOfOccurrences(numbers: List<String>, col: Int, oxygen: Boolean = true): Char {
            var count = 0
            numbers.forEach {
                count = checkCodeNumber(it[col].code, count)
            }

            val maxOcc = if (oxygen) {
                if (count >= 0) '1' else '0'
            } else if (count >= 0) '0' else '1'

            println("The col: $col, has the more occurrences of the number: $maxOcc")
            return maxOcc
        }

        fun getNumbersBasedOnOccurrences(numbers: List<String>, col: Int, numberToSearch: Char): List<String> {
            return numbers.filter { it[col] == numberToSearch }
        }
    }
}

fun main() {
    val lines = readFile("src/main/resources/input_3.txt")
    val lengthList = lines[0].length
    println("LIST length: $lengthList")

    var listToOxygen = lines
    var listToC02 = lines

    for (i in 0 until lengthList) {
        val maxOcc = getMostOfOccurrences(listToOxygen, i, true)
        if (listToOxygen.size > 1) {
            listToOxygen = getNumbersBasedOnOccurrences(listToOxygen, i, maxOcc)
        }
    }
    for (i in 0 until lengthList) {
        val maxOcc = getMostOfOccurrences(listToC02, i, false)
        if (listToC02.size > 1) {
            listToC02 = getNumbersBasedOnOccurrences(listToC02, i, maxOcc)
        }
    }

    val oxygenDecimal = binaryToDecimal(listToOxygen[0])
    val c20Decimal = binaryToDecimal(listToC02[0])

    println("Oxygen")
    println("Number binary: $listToOxygen")
    println("Number decimal: $oxygenDecimal")

    println("C20")
    println("Number binary: $listToC02")
    println("Number decimal: $c20Decimal")

    val total = oxygenDecimal * c20Decimal
    println("Total: $total")
}
