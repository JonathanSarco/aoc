package com.jonivan.aoc.y2021.d3

import com.jonivan.aoc.utils.CountUtils.Companion.binaryToDecimal
import com.jonivan.aoc.utils.CountUtils.Companion.checkCodeNumber
import java.io.File

/**
 * https://adventofcode.com/2021/day/3
 * part 1
 */
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

    File("src/main/resources/input_3.txt").useLines { lines ->
        lines.forEach {
            println(it)
            col1 = checkCodeNumber(it[0].code, col1)
            col2 = checkCodeNumber(it[1].code, col2)
            col3 = checkCodeNumber(it[2].code, col3)
            col4 = checkCodeNumber(it[3].code, col4)
            col5 = checkCodeNumber(it[4].code, col5)
            col6 = checkCodeNumber(it[5].code, col6)
            col7 = checkCodeNumber(it[6].code, col7)
            col8 = checkCodeNumber(it[7].code, col8)
            col9 = checkCodeNumber(it[8].code, col9)
            col10 = checkCodeNumber(it[9].code, col10)
            col11 = checkCodeNumber(it[10].code, col11)
            col12 = checkCodeNumber(it[11].code, col12)
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
    val firstDecimal = binaryToDecimal(firstArray)
    println("1st => $firstDecimal")

    val reversed = arrayOf("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0")
    resultArray.forEachIndexed { index, s -> reversed[index] = if (s == "0") "1" else "0" }

    val secondArray = reversed.reduce { acc, i -> acc + i }.toString()
    println("second array $secondArray")
    val secondDecimal = binaryToDecimal(secondArray)
    println("2nd => $secondDecimal")

    println("result => ${firstDecimal * secondDecimal}")
}

fun main() {
}
