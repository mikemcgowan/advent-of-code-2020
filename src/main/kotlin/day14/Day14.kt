package day14

import java.io.File
import kotlin.math.pow

const val len = 36
typealias Memory = MutableMap<Long, Long>

fun main() {
    val input = File("src/main/kotlin/day14/input.txt").readLines()
    val result1 = go(input, ::part1)
    println(result1)
    val result2 = go(input, ::part2)
    println(result2)
}

fun go(input: List<String>, f: (Memory, Int, String, Int) -> Unit): Long {
    val acc = mutableMapOf<Long, Long>()
    var mask = ""
    input.forEach { line ->
        val bits = line.split(' ')
        if (line.startsWith("mask")) {
            mask = bits[2]
        } else {
            val address = bits[0]
                .drop("mem".length + 1)
                .takeWhile { it.isDigit() }
                .toInt()
            f(acc, address, mask, bits[2].toInt())
        }
    }
    return acc.values.sum()
}

fun part1(acc: Memory, address: Int, mask: String, value: Int) {
    if (mask.length != len) return
    val xs = Integer
        .toBinaryString(value)
        .padStart(len, '0')
        .zip(mask)
        .map {
            when (it.second) {
                '0' -> '0'
                '1' -> '1'
                else -> it.first
            }
        }
    acc[address.toLong()] = charListToLong(xs)
}

fun part2(acc: Memory, address: Int, mask: String, value: Int) {
    if (mask.length != len) return
    val xs = Integer
        .toBinaryString(address)
        .padStart(len, '0')
        .zip(mask)
        .map {
            when (it.second) {
                '0' -> it.first
                '1' -> '1'
                else -> 'X'
            }
        }
    val bitCount = xs.count { it == 'X' }
    (0 until 2.0.pow(bitCount).toInt())
        .map { Integer.toBinaryString(it).padStart(bitCount, '0').toList() }
        .forEach {
            val addr = it.fold(xs) { ys, c ->
                ys.takeWhile { y -> y != 'X' }.plus(c).plus(ys.dropWhile { y -> y != 'X' }.drop(1))
            }
            acc[charListToLong(addr)] = value.toLong()
        }
}

fun charListToLong(xs: List<Char>): Long = xs
    .zip(xs.indices.reversed())
    .sumOf {
        when (it.first) {
            '1' -> 2.0.pow(it.second.toDouble()).toLong()
            else -> 0
        }
    }
