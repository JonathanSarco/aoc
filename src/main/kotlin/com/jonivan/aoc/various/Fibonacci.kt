package com.jonivan.aoc.various

private fun fibonacci(n: Int): Int {
    val map: MutableMap<Int, Int> = mutableMapOf()

    map[0] = 0
    map[1] = 1

    println("map $map")
    for (i in 2..n) {
        println("i $i")
        println("-1 ${map[i - 1]!!}")
        println("-2 ${map[i - 2]!!}")
        map[i] = map[i-1]!! + map[i-2]!!
        println("map $map")
    }

    return map[n]!!
}

fun main() {
    println("Fib ${fibonacci(10)}")
}
