package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils

enum class Numbers(val number: String, val value: Int) {
    ONE("one", 1),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five",5),
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
            text.forEach ending@ { it ->
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

private fun partOne(input: List<String>) {
    var totalResult = 0
    input.forEach {
        val number: String = WrittenDigits.getDigitOrWord(it, false).toString() + WrittenDigits.getDigitOrWord(it.reversed(), true)
        totalResult += number.toInt()
    }
    print("p1: $totalResult")
}

private fun partTwo(input: List<String>) {
    var totalResult = 0

    input.forEach {
        val number: String = WrittenDigits.getDigitOrWord(it, false).toString() + WrittenDigits.getDigitOrWord(it.reversed(), true)
        totalResult += number.toInt()
    }

    print("p2: $totalResult")
}


fun main() {
    val lines = InputUtils.readFileAsList("src/main/resources/2023/input_day_01.txt")
    partOne(lines)
    partTwo(lines)
}
