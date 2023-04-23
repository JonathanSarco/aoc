package com.jonivan.aoc.y2021.d2

import java.io.File

/**
 * https://adventofcode.com/2021/day/2
 */
enum class Movement {
    FORWARD,
    UP,
    DOWN,
}

data class Instruction(
    val mov: Movement,
    val quantity: Int,
)

var position: Triple<Int, Int, Int> = Triple(0, 0, 0)

fun doInstruction(instruction: Instruction) {
    position = when (instruction.mov) {
        Movement.FORWARD -> position.copy(
            first = position.first + instruction.quantity,
            second = position.second + (position.third * instruction.quantity),
            third = position.third,
        )

        Movement.DOWN -> position.copy(
            first = position.first,
            second = position.second,
            third = position.third + instruction.quantity,
        )

        Movement.UP -> position.copy(
            first = position.first,
            second = position.second,
            third = position.third - instruction.quantity,
        )
    }
    println("current position : $position")
}

fun main() {
    File("src/main/resources/input_2.txt").useLines { lines ->
        lines.forEach {
            val mov = it.split(" ")[0].trim()
            val quan = it.split(" ")[1].trim().toInt()
            doInstruction(Instruction(Movement.valueOf(mov), quan))
        }
    }
    println("Total: ${position.first * position.second}")
}
