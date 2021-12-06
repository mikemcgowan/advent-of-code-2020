package day3

import java.io.File

const val tree = '#'

fun main() {
    val inputs = File("src/main/kotlin/day3/input.txt").readLines()
    println(part1(inputs))
    println(part2(inputs))
}

fun part1(inputs: List<String>): Int =
    countTrees(inputs, 3, false)

fun part2(inputs: List<String>): Long =
    listOf(
        countTrees(inputs, 1, false),
        countTrees(inputs, 3, false),
        countTrees(inputs, 5, false),
        countTrees(inputs, 7, false),
        countTrees(inputs, 1, true),
    ).foldRight(1L) { x, acc -> x * acc }

fun countTrees(inputs: List<String>, inc: Int, alternate: Boolean): Int {
    var x = 0
    var trees = 0
    var doNext = true
    inputs.forEach {
        if (doNext) {
            val i = x % it.length
            if (it[i] == tree) ++trees
            x += inc
        }
        if (alternate) doNext = !doNext
    }
    return trees
}
