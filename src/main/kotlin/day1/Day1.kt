package day1

import java.io.File

var target = 2020

fun main() {
    val inputs = File("src/main/kotlin/day1/input.txt").readLines().map { it.toInt() }
    println(part1(inputs))
    println(part2(inputs))
}

fun part1(inputs: List<Int>): Int? {
    inputs.forEach { i ->
        inputs.forEach { j ->
            if (i != j && i + j == target)
                return i * j
        }
    }
    return null
}

fun part2(inputs: List<Int>): Int? {
    inputs.forEach { i ->
        inputs.forEach { j ->
            inputs.forEach { k ->
                if (i != j && i != k && j != k && i + j + k == target)
                    return i * j * k
            }
        }
    }
    return null
}
