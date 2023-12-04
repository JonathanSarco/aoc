package com.jonivan.aoc.utils

import java.util.concurrent.TimeUnit

class TimeCalculator {
    companion object {
        fun withTIme(fn: () -> Unit) {
            val initTimeP1 = System.nanoTime()
            fn()
            val finishTimeP1 = System.nanoTime()
            println("--> Total time (ns): ${TimeUnit.NANOSECONDS.toMillis(finishTimeP1 - initTimeP1)}")
            println()
        }
    }
}