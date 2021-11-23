package day13

import java.io.File
import java.math.BigInteger
import kotlin.math.ceil

fun Int.toBigInteger(): BigInteger = BigInteger.valueOf(toLong())

fun main() {
    val (earliest, timetable) = File("src/main/kotlin/day13/input.txt").readLines()
    val buses1 = timetable
        .split(',')
        .filter { it != "x" }
        .map { it.toInt() }
    val result1 = part1(earliest.toInt(), buses1)
    println(result1)
    val buses2 = timetable
        .split(',')
        .zip(timetable.indices)
        .filter { it.first != "x" }
        .map { Pair(it.first.toInt(), it.second) }
    val result2 = part2(buses2)
    println(result2)
}

fun part1(earliest: Int, buses: List<Int>): Int? = buses
    .map { Pair(it, it * ceil(earliest.toDouble() / it.toDouble()).toInt() - earliest) }
    .minByOrNull { it.second }
    ?.let {
        it.first * it.second
    }

fun part2(buses: List<Pair<Int, Int>>): BigInteger {
    val otherBuses = buses.drop(1).map { Pair(it.first.toBigInteger(), it.second.toBigInteger()) }
    var inc = buses.first().first.toBigInteger()
    var earliest = BigInteger.ZERO
    otherBuses.forEach {
        while ((earliest + it.second) % it.first != BigInteger.ZERO) {
            earliest += inc
        }
        inc *= it.first
    }
    return earliest
}
