package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.ListsUtils
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
//        println("my winning number $mwn")
        totalValue += calculateValue(mwn)
    }
    println("total $totalValue")
}


private fun partTwo(input: List<String>) {
    val totalCards = 0
    val cardsToCopy: MutableList<Int> = mutableListOf()
    input.forEach { card ->
        val numbers = card.split(": ")[1].split(" | ")
        val cardNumber = Card.getCardId(card.split(": ")[0])

        val winningNumbers = numbers[0].split(" ").filter { it.isNotEmpty() }.map { it.trim() }.map { it.toInt() }
        val personalNumbers = numbers[1].split(" ").filter { it.isNotEmpty() }.map { it.trim() }.map { it.toInt() }

        val mwn = getMyWinningNumbers(winningNumbers, personalNumbers)
        //println("winning numbers $mwn")

        mwn.forEachIndexed { index, _ ->
            cardsToCopy.add(cardNumber + index + 1)
        }
        cardsToCopy.sort()

        //println("before filtering cards to copy $cardsToCopy")

        val sameNumber = cardsToCopy.filter { copy -> copy == cardNumber }
//        println("numbers on copy $sameNumber")

        sameNumber.forEach { _ -> mwn.forEachIndexed { index, _ ->
            cardsToCopy.add(cardNumber + index + 1)
        } }
        cardsToCopy.sort()

//        println("cards to copy $cardsToCopy")
    }

    val result = input.size + cardsToCopy.size
    println("total result $result")
}


fun main() {
    val lines = ListsUtils.readFile("src/main/resources/2023/input_day_04.txt")
    val initTimeP1 = System.nanoTime()
    partOne(lines)
    val finishTimeP1 = System.nanoTime()
    val totalTime: Long = TimeUnit.NANOSECONDS.toMillis(finishTimeP1 - initTimeP1)
    println("P1: $totalTime ms")

    val initTimeP2 = System.nanoTime()
    partTwo(lines)
    val finishTimeP2 = System.nanoTime()
    val totalTime2: Long = TimeUnit.NANOSECONDS.toMillis(finishTimeP2 - initTimeP2)
    println("P2: $totalTime2 ms")
}