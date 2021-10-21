package day7

import java.io.File

data class Bag(private val adjective: String, private val colour: String)

val shinyGold = Bag("shiny", "gold")
val bags = HashMap<Bag, List<Pair<Int, Bag>>>();
val whitespace = Regex("""\s+""")
val bagSeparator = Regex("""\s+bag(,|s,|\.|s\.)""")

fun main() {
    // parse
    val lines = File("src/main/kotlin/day7/input.txt").readLines()
    lines.filter { it.isNotBlank() }.forEach { line ->
        val words = line.split(whitespace)
        val contents = words.drop(4).joinToString(" ").split(bagSeparator).dropLast(1)
        bags[Bag(words[0], words[1])] = contents.mapNotNull { item ->
            when (item) {
                "no other" -> null
                else -> {
                    val triplet = item.split(whitespace).filter { it.isNotBlank() }
                    val bag = Bag(triplet[1], triplet[2])
                    Pair(triplet[0].toInt(), bag)
                }
            }
        }
    }

    // part 1
    val part1Result = bags.count { containsBag(it.value) }
    println(part1Result)

    // part 2
    val part2Result = countBags(bags[shinyGold]!!)
    println(part2Result)
}

fun containsBag(contents: List<Pair<Int, Bag>>): Boolean =
    contents.any { it.second == shinyGold || containsBag(bags[it.second]!!) }

fun countBags(contents: List<Pair<Int, Bag>>): Int =
    contents.sumOf { it.first + it.first * countBags(bags[it.second]!!) }
