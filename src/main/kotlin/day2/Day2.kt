package day2

import java.io.File

data class PasswordWithPolicy(val range: IntRange, val letter: Char, val password: String)

fun main() {
    val passwords = File("src/main/kotlin/day2/input.txt").readLines().map { parse(it) }
    println(passwords.count { part1(it) })
    println(passwords.count { part2(it) })
}

fun parse(line: String): PasswordWithPolicy {
    val chunks = line.split(' ')
    val minMax = chunks[0].split('-')
    val range = IntRange(minMax[0].toInt(), minMax[1].toInt())
    val letter = chunks[1][0]
    val password = chunks[2]
    return PasswordWithPolicy(range, letter, password)
}

fun part1(passwordWithPolicy: PasswordWithPolicy): Boolean {
    val freq = passwordWithPolicy.password.count { it == passwordWithPolicy.letter }
    return freq in passwordWithPolicy.range
}

fun part2(passwordWithPolicy: PasswordWithPolicy): Boolean {
    val first = passwordWithPolicy.password[passwordWithPolicy.range.first - 1] == passwordWithPolicy.letter
    val second = passwordWithPolicy.password[passwordWithPolicy.range.last - 1] == passwordWithPolicy.letter
    return first xor second
}
