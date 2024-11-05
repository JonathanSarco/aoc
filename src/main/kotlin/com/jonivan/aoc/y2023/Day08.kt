package com.jonivan.aoc.y2023

import com.jonivan.aoc.utils.InputUtils
import com.jonivan.aoc.utils.MathUtils.Companion.leastCommonMultiple
import kotlin.time.measureTime

private data class Node(val left: String, val right: String) {
    fun getStep(dir: Char): String {
        return if (dir == 'L') this.left else this.right
    }
}

private fun getNodes(input: List<String>): List<Map<String, Node>> {
    return input.subList(2, input.size)
        .map { raw ->
            val start = raw.split("=")[0].trim()
            val left = raw.split("=")[1].split(",")[0].replace("(", "").trim()
            val right = raw.split("=")[1].split(",")[1].replace(")", "").trim()
            mapOf(start to Node(left, right))
        }
}

private tailrec fun nav(nodes: List<Map<String, Node>>, currentStep: String, direction: List<Char>, step: Long, posDirection: Int, predicate: (String) -> Boolean): Long {
    if (predicate(currentStep)) {
        return step
    }
    val position = nodes.first { it.containsKey(currentStep) }
    val nextStep = position[currentStep]!!.getStep(direction[posDirection])
    return nav(nodes, nextStep, direction, step + 1, if (posDirection < direction.size - 1) posDirection + 1 else 0, predicate)
}

private fun partOne(input: List<String>) {
    val directions = input[0].toList()

    val result = nav(getNodes(input), "AAA", directions, 0, 0) { it == "ZZZ" }
    println("P1 result: $result")
}

private fun getNodesWithEnding(nodes: List<Map<String, Node>>, ending: String): List<String> {
    return nodes.filter { node -> node.keys.first().endsWith(ending) }.map { fn -> fn.keys.first() }
}

private fun partTwo(input: List<String>) {
    val directions = input[0].toList()
    val nodes = getNodes(input)
    val nodesWithEnding = getNodesWithEnding(nodes, "A")
    val allSteps = nodesWithEnding.map { currentStep ->
        val sum: Long = nav(nodes, currentStep, directions, 0, 0) { it.endsWith("Z") }
        sum
    }
    val result = allSteps.reduce { acc, i -> leastCommonMultiple(acc, i) }

    println("P2 result: $result")
}

fun main() {
    val input = InputUtils.readFileAsListString("src/main/resources/2023/input_day_08.txt")

    println("P1 time:  ${measureTime { partOne(input) }}")
    println("P2 time:  ${measureTime { partTwo(input) }}")
}