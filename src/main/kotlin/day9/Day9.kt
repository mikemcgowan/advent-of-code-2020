package day9

import java.io.File

val preamble = 25

fun main() {
    val lines = File("src/main/kotlin/day9/input.txt").readLines().map { it.toLong() }
    part2(lines, part1(lines))
}

fun part1(lines: List<Long>): Long {
    val x = lines.windowed(preamble + 1).filterNot { xs ->
        val target = xs.last()
        val zs = xs.dropLast(1)
        var found = false
        zs.forEach { z1 ->
            zs.forEach { z2 ->
                if (z1 + z2 == target) found = true
            }
        }
        found
    }.first().last()
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
