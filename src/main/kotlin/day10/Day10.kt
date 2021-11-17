package day10

import java.io.File

const val final = 3

fun main() {
    val input = File("src/main/kotlin/day10/input.txt").readLines().map { it.toInt() }
    val sortedInput = input.sorted()
    val xs = listOf(0).plus(sortedInput).plus(sortedInput.last() + final)
    val p1 = part1(xs)
    println(p1)
    val p2 = part2(xs)
    println(p2)
}

fun part1(input: List<Int>): Int {
    val m = mutableMapOf<Int, Int>()
    input
        .windowed(2)
        .forEach { m.compute(it[1] - it[0]) { _, v -> if (v == null) 1 else (v + 1) } }
    return m[1]!! * m[3]!!
}

fun part2(input: List<Int>): Long? {
    val xs: MutableMap<Int, Long> = mutableMapOf(0 to 1L)
    input.drop(1).forEach {
        xs[it] = (1..3).sumOf { delta -> xs.getOrDefault(it - delta, 0) }
    }
    return xs[input.last()]
}
