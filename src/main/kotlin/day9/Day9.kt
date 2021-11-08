package day9

import java.io.File

val preamble = 25

fun main() {
    val lines = File("src/main/kotlin/day9/input.txt").readLines().map { it.toLong() }
    val p1 = part1(lines)
    println(p1)
    val p2 = part2(lines, p1)
    println(p2)
}

fun List<Long>.hasPairOfSum(sum: Long): Boolean =
    indices.any { i ->
        indices.any { j ->
            i != j && this[i] + this[j] == sum
        }
    }

fun part1(lines: List<Long>): Long = lines
    .asSequence()
    .windowed(preamble + 1)
    .filterNot { it.dropLast(1).hasPairOfSum(it.last()) }
    .first()
    .last()

fun part2(lines: List<Long>, target: Long): Long? {
    (2..lines.size).forEach { w ->
        lines
            .asSequence()
            .windowed(w)
            .firstOrNull { it.sum() == target }
            ?.let {
                val ys = it.sorted()
                return ys.first() + ys.last()
            }
    }
    return null
}
