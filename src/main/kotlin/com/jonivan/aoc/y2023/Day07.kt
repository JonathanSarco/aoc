package com.jonivan.aoc.y2023

import com.jonivan.aoc.base.Solution
import com.jonivan.aoc.utils.InputUtils


class Day07: Solution<List<String>>() {

    private enum class HandType {
        FIVE_PAIR, FOUR_PAIR, FULL_HOUSE, THREE_PAIR, TWO_PAIR, ONE_PAIR, HIGH_CARD, NULL_VALUE
    }

    private data class CardBit(val card: String, val bit: Long, var type: HandType = HandType.HIGH_CARD) {

        companion object {
            fun cardWeightNoJoker(c: Char): Int {
                return when(c) {
                    'A' -> 14
                    'K' -> 13
                    'Q' -> 12
                    'T' -> 10
                    '9' -> 9
                    '8' -> 8
                    '7' -> 7
                    '6' -> 6
                    '5' -> 5
                    '4' -> 4
                    '3' -> 3
                    '2' -> 2
                    'J' -> 0
                    else -> -1
                }
            }

            fun defineHand(card: String): HandType {
                val result = card.groupBy { it }
                val groupSizeCard = result.size

                if (groupSizeCard == 1) return HandType.FIVE_PAIR
                if (groupSizeCard == 2) {
                    val minGrouped = result.minBy { it.value.size }
                    return if (minGrouped.value.size == 2) HandType.FULL_HOUSE else HandType.FOUR_PAIR
                }
                if (groupSizeCard == 3) {
                    val maxGrouped = result.maxBy { it.value.size }
                    val minGrouped = result.minBy { it.value.size }
                    return if (maxGrouped.value.size == 3 && minGrouped.value.size == 1) HandType.THREE_PAIR else HandType.TWO_PAIR
                }

                if (groupSizeCard == 4) return HandType.ONE_PAIR
                if (groupSizeCard == 5 || groupSizeCard == 0) return HandType.HIGH_CARD

                return HandType.NULL_VALUE
            }
        }
    }

    private fun getCardsAndBits(input: List<String>): List<CardBit> {
        val formatted: MutableList<CardBit> = mutableListOf()

        input.forEach {
            val split = it.split(" ")
            val card = split[0]
            val bit = split[1].trim().toLong()
            formatted.add(CardBit(card, bit))
        }
        return formatted
    }

    private fun orderByCards(cardsWithTempRank: List<CardBit>): List<CardBit> {
        val ordered = cardsWithTempRank.sortedBy { it.type }.reversed()

        val grouped = ordered.groupBy { it.type }

        val comparator = Comparator { c1: CardBit, c2: CardBit ->
            c1.card.zip(c2.card)
                .firstOrNull { (c1, c2) -> c1 != c2 }
                ?.let { (c1, c2) -> CardBit.cardWeightNoJoker(c1).compareTo(CardBit.cardWeightNoJoker(c2))
                } ?: 0
        }

        val result: MutableList<CardBit> = mutableListOf()

        grouped.forEach { entry ->
            result.addAll(entry.value.sortedWith(comparator))
        }

        return result
    }


    override fun partOne(input: List<String>): Int {
        val cardAndBit = getCardsAndBits(input)
        val cardsWithTempRank: MutableList<CardBit> = mutableListOf()

        cardAndBit.forEach {
            cardsWithTempRank.add(CardBit(it.card, it.bit, CardBit.defineHand(card = it.card) ))
        }

        val orderedCards = orderByCards(cardsWithTempRank)

        var totalResult = 0L
        orderedCards.forEachIndexed { index, cardBit ->
            totalResult += (index + 1).times(cardBit.bit)
        }
        return totalResult.toInt()
    }

    override fun partTwo(input: List<String>): Int {
        val cardAndBit = getCardsAndBits(input)
        val finalCards: MutableList<CardBit> = mutableListOf()

        cardAndBit.forEach { cardBit ->
            var replaced = ""
            val grouped = cardBit.card.groupBy { it }

            if (grouped.size < 5) {
                val filtered = cardBit.card.groupBy { it }.filter { a -> a.key != 'J' }
                replaced = if (filtered.isNotEmpty()) {
                    val toReplace = filtered.maxBy { it.value.size }.key
                    cardBit.card.replace('J', toReplace)
                } else {
                    cardBit.card
                }
            }
            finalCards.add(CardBit(cardBit.card, cardBit.bit, CardBit.defineHand(card = replaced) ))
        }

        val orderedCards = orderByCards(finalCards)

        var totalResult = 0L
        orderedCards.forEachIndexed { index, cardBit ->
            totalResult += (index + 1).times(cardBit.bit)
        }
        return totalResult.toInt()
    }

}

fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_07.txt")
    val day = Day07()
    day.solvePart1(input)
    day.solvePart2(input)
}


