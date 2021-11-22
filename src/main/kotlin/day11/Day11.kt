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
    val result1 = solve(input, cols, rows, ::occupiedAdjacent, 4)
    println(result1)
    val result2 = solve(input, cols, rows, ::occupiedLineOfSight, 5)
    println(result2)
}

fun solve(grid: Grid, cols: Int, rows: Int, f: (Grid, Int, Int, Int, Int) -> Int, toEmpty: Int): Int {
    var current: Grid
    var next = grid
    do {
        current = next
        next = applyRules(current, cols, rows, f, toEmpty)
    } while (!gridsSame(current, next))
    return countOccupied(next)
}

fun applyRules(grid: Grid, cols: Int, rows: Int, f: (Grid, Int, Int, Int, Int) -> Int, toEmpty: Int): Grid =
    grid.indices.map { row ->
        grid[row].indices.map { col ->
            val occupied = f(grid, cols, rows, col, row)
            when (grid[row][col]) {
                Place.EMPTY -> if (occupied == 0) Place.OCCUPIED else Place.EMPTY
                Place.OCCUPIED -> if (occupied >= toEmpty) Place.EMPTY else Place.OCCUPIED
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

fun occupiedLineOfSight(grid: Grid, cols: Int, rows: Int, col: Int, row: Int): Int {
    val mR = row - 1 downTo 0 // minus row (up)
    val pR = row + 1 until rows // plus row (down)
    val mC = col - 1 downTo 0 // minus col (left)
    val pC = col + 1 until cols // plus col (right)
    return listOf(
        mR.map { Pair(it, col) }, pR.map { Pair(it, col) }, // straight up and down
        mC.map { Pair(row, it) }, pC.map { Pair(row, it) }, // straight left and right
        mR.zip(mC), mR.zip(pC), // diagonal up left and up right
        pR.zip(mC), pR.zip(pC), // diagonal down left and down right
    )
        .map { line -> line.map { grid[it.first][it.second] } }
        .count { line -> line.firstOrNull { it != Place.FLOOR } == Place.OCCUPIED }
}

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
