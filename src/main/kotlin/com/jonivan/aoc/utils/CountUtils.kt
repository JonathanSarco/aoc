package com.jonivan.aoc.utils

class CountUtils {
    companion object {
        fun checkCodeNumber(codeNumber: Int, count: Int): Int {
            // number 49 = positive
            return if (codeNumber == 49) {
                count + 1
            } else {
                count - 1
            }
        }

        fun binaryToDecimal(n: String): Int {
            var decValue = 0
            var base = 1
            val len = n.length
            for (i in len - 1 downTo 0) {
                if (n[i] == '1') decValue += base
                base *= 2
            }
            return decValue
        }
    }
}
