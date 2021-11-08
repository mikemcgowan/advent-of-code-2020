package day9

import java.io.File

val preamble = 25

fun main() {
    val lines = File("src/main/kotlin/day9/input.txt").readLines().map { it.toLong() }
    part2(lines, part1(lines))
}

fun List<Long>.hasPairOfSum(sum: Long): Boolean =
    indices.any { i ->
        indices.any { j ->
            i != j && this[i] + this[j] == sum
        }
    }

fun part1(lines: List<Long>): Long {
    val x = lines
        .windowed(preamble + 1)
        .filterNot { it.dropLast(1).hasPairOfSum(it.last()) }
        .first()
        .last()
    println(x)
    return x
}

fun part2(lines: List<Long>, target: Long) {
    (2..lines.size).forEach { w ->
        lines.windowed(w).firstOrNull { it.sum() == target }?.let {
            val ys = it.sorted()
            println(ys.first() + ys.last())
            return
        }
    }
}
