package day6

import java.io.File

fun main() {
    val all = File("src/main/kotlin/day6/input.txt").readText()
    val groups = all.split("\n\n").map { it.lines() }
    val part1 = groups.sumOf { group ->
        group.joinToString("").toSet().size
    }
    println(part1)
    val part2 = groups.sumOf { group ->
        group.joinToString("").toSet().count { c ->
            group.filter { it.isNotBlank() }.all { it.contains(c) }
        }
    }
    println(part2)
}
