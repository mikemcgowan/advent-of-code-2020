import java.io.File
import kotlin.math.pow

fun main() {
    val lines = File("src/main/kotlin/day5/input.txt").readLines()
    val seatIds = lines.map { boardingPassToSeatId(it) }
    val min = seatIds.minOrNull()
    val max = seatIds.maxOrNull()
    println(max) // part 1
    val oneGap = seatIds.toSortedSet()
    val noGaps = (min!!..max!!).toSortedSet()
    println(noGaps.minus(oneGap).first()) // part 2
}

fun boardingPassToSeatId(boardingPass: String): Int {
    val row = findPos(boardingPass.take(7), 'F', 'B')
    val col = findPos(boardingPass.drop(7), 'L', 'R')
    return row * 8 + col
}

fun findPos(info: String, lower: Char, upper: Char): Int {
    val limit = 2.0.pow(info.length).toInt() - 1
    return info.fold((0..limit).toList()) { acc, c ->
        when (c) {
            lower -> acc.take(acc.size / 2)
            upper -> acc.drop(acc.size / 2)
            else -> acc
        }
    }.first()
}
