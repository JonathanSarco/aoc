package com.jonivan.aoc.y2023

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils
import com.jonivan.aoc.utils.InputUtils.Companion.convertToListString
import java.io.File

class Day01 : Solution<List<String>, Int>() {
    enum class Numbers(val number: String, val value: Int) {
        ONE("one", 1),
        TWO("two", 2),
        THREE("three", 3),
        FOUR("four", 4),
        FIVE("five", 5),
        SIX("six", 6),
        SEVEN("seven", 7),
        EIGHT("eight", 8),
        NINE("nine", 9),
    }

    /**
     * Second part
     */
    class WrittenDigits {
        companion object {
            fun getDigitOrWord(text: String, backwards: Boolean): Int {
                var finalResult = ""
                var word = ""
                text.forEach ending@{ it ->
                    if (finalResult == "") {
                        if (it.isDigit()) {
                            finalResult += it
                            return@ending
                        }

                        word = if (backwards) it + word else word + it

                        val enumValue = Numbers.entries.firstOrNull { ev -> word.contains(ev.number) }?.value
                        if (enumValue != null) {
                            finalResult = enumValue.toString()
                            return@ending
                        }
                    }
                }
                return finalResult.toInt()
            }
        }

    }

    /**
     * First part
     */
    class Digits {
        companion object {
            private fun getDigit(text: String): Int? {
                return text.firstOrNull { it.isDigit() }?.digitToInt()
            }

            fun getDigits(text: String): Int {
                val finalNumber: String = getDigit(text = text).toString() + getDigit(text = text.reversed()).toString()
                return finalNumber.toInt()
            }
        }
    }

    override fun partOne(input: List<String>): Int {
        var totalResult = 0
        input.forEach {
            val number: String =
                WrittenDigits.getDigitOrWord(it, false).toString() + WrittenDigits.getDigitOrWord(it.reversed(), true)
            totalResult += number.toInt()
        }
        return totalResult
    }

    override fun partTwo(input: List<String>): Int {
        var totalResult = 0

        input.forEach {
            val number: String =
                WrittenDigits.getDigitOrWord(it, false).toString() + WrittenDigits.getDigitOrWord(it.reversed(), true)
            totalResult += number.toInt()
        }

        return totalResult
    }
}


fun main() {
//    val lines = InputUtils.readFileAsListString("src/main/resources/2023/input_day_01.txt")
    val lines = File("src/main/resources/2023/input_day_01.txt").convertToListString()
    val day = Day01()
    day.solvePart1(lines)
    day.solvePart2(lines)
}
