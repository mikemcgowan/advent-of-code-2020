package day11

import java.io.File

enum class Place {
    FLOOR, EMPTY, OCCUPIED;

    companion object {
        fun fromChar(c: Char): Place =
            when (c) {
                'L' -> EMPTY
                '#' -> OCCUPIED
                else -> FLOOR
            }
    }
}

typealias Grid = List<List<Place>>

fun main() {
    val input = File("src/main/kotlin/day11/input.txt")
        .readLines()
        .map { line -> line.map { Place.fromChar(it) } }
    val cols = input[0].size
    val rows = input.size
    val result1 = part1(input, cols, rows)
    println(result1)
}

fun part1(grid: Grid, cols: Int, rows: Int): Int {
    var current: Grid
    var next = grid
    do {
        current = next
        next = applyRules(current, cols, rows)
    } while (!gridsSame(current, next))
    return countOccupied(next)
}

fun applyRules(grid: Grid, cols: Int, rows: Int): Grid =
    grid.indices.map { row ->
        grid[row].indices.map { col ->
            val occupiedAdjacent = occupiedAdjacent(grid, cols, rows, col, row)
            when (grid[row][col]) {
                Place.EMPTY -> if (occupiedAdjacent == 0) Place.OCCUPIED else Place.EMPTY
                Place.OCCUPIED -> if (occupiedAdjacent >= 4) Place.EMPTY else Place.OCCUPIED
                else -> grid[row][col]
            }
        }
    }

fun occupiedAdjacent(grid: Grid, cols: Int, rows: Int, col: Int, row: Int): Int =
    listOf(
        row - 1 to col - 1, row - 1 to col + 0, row - 1 to col + 1,
        row + 0 to col - 1, /*               */ row + 0 to col + 1,
        row + 1 to col - 1, row + 1 to col + 0, row + 1 to col + 1
    )
        .filter { it.first in 0 until rows && it.second in 0 until cols }
        .count { grid[it.first][it.second] == Place.OCCUPIED }

fun countOccupied(grid: Grid): Int =
    grid.indices.sumOf { row ->
        grid[row].indices.count { col ->
            grid[row][col] == Place.OCCUPIED
        }
    }

fun gridsSame(grid1: Grid, grid2: Grid): Boolean =
    grid1.indices.all { row ->
        grid1[row].indices.all { col ->
            grid1[row][col] == grid2[row][col]
        }
    }
