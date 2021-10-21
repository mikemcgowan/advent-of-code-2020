import java.io.File

const val fieldCount = 8
val eyeColours = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun main() {
    val all = File("src/main/kotlin/day4/input.txt").readText()
    val xs = all.split("\n\n").map { it.replace('\n', ' ') }
    val passports = xs.map {
        it
            .split(' ')
            .filter { field -> field.isNotBlank() }
            .associate { field -> Pair(field.split(':')[0], field.split(':')[1]) }
    }
    val part1Valid = part1(passports)
    println(part1Valid.size)
    val part2Valid = part2(part1Valid)
    println(part2Valid.size)
}

fun part1(passports: List<Map<String, String>>): List<Map<String, String>> =
    passports.filter { passport ->
        passport.keys.size == fieldCount || passport.keys.size == (fieldCount - 1) && !passport.keys.contains("cid")
    }

fun part2(passports: List<Map<String, String>>): List<Map<String, String>> =
    passports.filter { passport ->
        listOfNotNull(
            birthYearValid(passport["byr"]),
            issueYearValid(passport["iyr"]),
            expiryYearValid(passport["eyr"]),
            heightValid(passport["hgt"]),
            hairColourValid(passport["hcl"]),
            eyeColourValid(passport["ecl"]),
            pidValid(passport["pid"])
        ).all { it }
    }

fun birthYearValid(byr: String?) = byr?.let { it.length == 4 && it.toInt() in 1920..2002 }
fun issueYearValid(iyr: String?) = iyr?.let { it.length == 4 && it.toInt() in 2010..2020 }
fun expiryYearValid(eyr: String?) = eyr?.let { it.length == 4 && it.toInt() in 2020..2030 }
fun hairColourValid(hcl: String?) = hcl?.matches(Regex("""#[0-9a-f]{6}"""))
fun eyeColourValid(ecl: String?) = ecl?.let { eyeColours.contains(it) }
fun pidValid(pid: String?) = pid?.matches(Regex("""\d{9}"""))

fun heightValid(hgt: String?) = hgt?.let {
    when (it.takeLast(2)) {
        "cm" -> it.dropLast(2).toInt() in 150..193
        "in" -> it.dropLast(2).toInt() in 59..76
        else -> false
    }
}
