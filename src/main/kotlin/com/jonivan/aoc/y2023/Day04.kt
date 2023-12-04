package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.ListsUtils
import com.jonivan.aoc.utils.TimeCalculator
import java.util.concurrent.TimeUnit


private class Card {
    companion object {
        fun getCardId(text: String): Int {
            return text.split("Card ")[1].trim() .toInt()
        }
    }
}


private fun getMyWinningNumbers(winningNumbers: List<Int>, myNumbers: List<Int>): List<Int> {
    return myNumbers.filter { pn -> winningNumbers.any { wn -> wn == pn } }
}

private fun calculateValue(numbers: List<Int>): Int {
    val size = numbers.size

    if (size == 0) return 0

    var total = 1
    for (i in 1..<size) {
        total *= 2
    }
    return total
}

private fun partOne(input: List<String>) {
    var totalValue = 0

    input.forEach { card ->
        val numbers = card.split(": ")[1].split(" | ")

        val winningNumbers = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.trim() }.map { it.toInt() }
        val personalNumbers = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.trim() }.map { it.toInt() }

        val mwn = getMyWinningNumbers(winningNumbers, personalNumbers)
        totalValue += calculateValue(mwn)
    }
    println("P1 result: $totalValue")
}


private fun partTwo(input: List<String>) {
    val cardsToCopy: MutableList<Int> = mutableListOf()
    input.forEach { card ->
        val numbers = card.split(": ")[1].split(" | ")
        val cardNumber = Card.getCardId(card.split(": ")[0])

        val winningNumbers = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.trim() }.map { it.toInt() }
        val personalNumbers = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.trim() }.map { it.toInt() }

        val mwn = getMyWinningNumbers(winningNumbers, personalNumbers)

        mwn.forEachIndexed { index, _ ->
            cardsToCopy.add(cardNumber + index + 1)
        }

        val sameNumber = cardsToCopy.filter { copy -> copy == cardNumber }

        sameNumber.forEach { _ -> mwn.forEachIndexed { index, _ ->
            cardsToCopy.add(cardNumber + index + 1)
        } }
    }

    val result = input.size + cardsToCopy.size
    println("P2 result: $result")
}


fun main() {
    val lines = ListsUtils.readFile("src/main/resources/2023/input_day_04.txt")

    println("---- Day 04 ----")
    TimeCalculator.withTIme { partOne(lines) }
    TimeCalculator.withTIme { partTwo(lines) }
}