package day8

import java.io.File

data class Instr(val code: String, val amount: Int)

fun main() {
    // parse
    val lines = File("src/main/kotlin/day8/input.txt").readLines()
    val instrs = lines.map {
        val (code, amount) = it.split(' ')
        Instr(code, amount.toInt())
    }

    // part 1
    val part1Result = runInstrs(instrs, null)
    println(part1Result.first)

    // part 2
    val flipCodes = listOf("jmp", "nop")
    val part2Result = instrs.indices
        .filter { instrs[it].code in flipCodes }
        .map { runInstrs(instrs, it) }
        .first { it.second >= instrs.size }
    println(part2Result.first)
}

fun runInstrs(instrs: List<Instr>, flip: Int?): Pair<Int, Int> {
    val seen = mutableSetOf<Int>()
    var acc = 0
    var cur = 0
    while (cur !in seen && cur < instrs.size) {
        seen.add(cur)
        val instr = instrs[cur]
        var code = instr.code
        if (flip == cur) code = flipCode(code)
        cur += if (code == "jmp") instr.amount else 1
        acc += if (code == "acc") instr.amount else 0
    }
    return Pair(acc, cur)
}

fun flipCode(code: String): String =
    when (code) {
        "jmp" -> "nop"
        "nop" -> "jmp"
        else -> code
    }
