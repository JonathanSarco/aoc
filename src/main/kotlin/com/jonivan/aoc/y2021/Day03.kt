package com.jonivan.aoc.y2021

import com.jonivan.aoc.utils.CountUtils
import com.jonivan.aoc.utils.InputUtils
import com.jonivan.aoc.y2021.Occurrences.Companion.getMostOfOccurrences
import com.jonivan.aoc.y2021.Occurrences.Companion.getNumbersBasedOnOccurrences
import java.io.File

// TODO: Refactor this code
fun part1() {
    var col1 = 0
    var col2 = 0
    var col3 = 0
    var col4 = 0
    var col5 = 0
    var col6 = 0
    var col7 = 0
    var col8 = 0
    var col9 = 0
    var col10 = 0
    var col11 = 0
    var col12 = 0

    val resultArray: Array<String> = arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0")

    File("src/main/resources/input_day_03.txt").useLines { lines ->
        lines.forEach {
            println(it)
            col1 = CountUtils.checkCodeNumber(it[0].code, col1)
            col2 = CountUtils.checkCodeNumber(it[1].code, col2)
            col3 = CountUtils.checkCodeNumber(it[2].code, col3)
            col4 = CountUtils.checkCodeNumber(it[3].code, col4)
            col5 = CountUtils.checkCodeNumber(it[4].code, col5)
            col6 = CountUtils.checkCodeNumber(it[5].code, col6)
            col7 = CountUtils.checkCodeNumber(it[6].code, col7)
            col8 = CountUtils.checkCodeNumber(it[7].code, col8)
            col9 = CountUtils.checkCodeNumber(it[8].code, col9)
            col10 = CountUtils.checkCodeNumber(it[9].code, col10)
            col11 = CountUtils.checkCodeNumber(it[10].code, col11)
            col12 = CountUtils.checkCodeNumber(it[11].code, col12)
        }
    }

    resultArray[0] = if (col1 > 0) "1" else "0"
    resultArray[1] = if (col2 > 0) "1" else "0"
    resultArray[2] = if (col3 > 0) "1" else "0"
    resultArray[3] = if (col4 > 0) "1" else "0"
    resultArray[4] = if (col5 > 0) "1" else "0"
    resultArray[5] = if (col6 > 0) "1" else "0"
    resultArray[6] = if (col7 > 0) "1" else "0"
    resultArray[7] = if (col8 > 0) "1" else "0"
    resultArray[8] = if (col9 > 0) "1" else "0"
    resultArray[9] = if (col10 > 0) "1" else "0"
    resultArray[10] = if (col11 > 0) "1" else "0"
    resultArray[11] = if (col12 > 0) "1" else "0"

    val firstArray = resultArray.reduce { acc, string -> acc + string }.toString()
    println("first array $firstArray")
    val firstDecimal = CountUtils.binaryToDecimal(firstArray)
    println("1st => $firstDecimal")

    val reversed = arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0")
    resultArray.forEachIndexed { index, s -> reversed[index] = if (s == "0") "1" else "0" }

    val secondArray = reversed.reduce { acc, i -> acc + i }.toString()
    println("second array $secondArray")
    val secondDecimal = CountUtils.binaryToDecimal(secondArray)
    println("2nd => $secondDecimal")

    println("result => ${firstDecimal * secondDecimal}")
}

class Occurrences {
    companion object {
        fun getMostOfOccurrences(numbers: List<String>, col: Int, oxygen: Boolean = true): Char {
            var count = 0
            numbers.forEach {
                count = CountUtils.checkCodeNumber(it[col].code, count)
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
    val lines = InputUtils.readFileAsListString("src/main/resources/input_day_03.txt")
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

    val oxygenDecimal = CountUtils.binaryToDecimal(listToOxygen[0])
    val c20Decimal = CountUtils.binaryToDecimal(listToC02[0])

    println("Oxygen")
    println("Number binary: $listToOxygen")
    println("Number decimal: $oxygenDecimal")

    println("C20")
    println("Number binary: $listToC02")
    println("Number decimal: $c20Decimal")

    val total = oxygenDecimal * c20Decimal
    println("Total: $total")
}
