fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { getPrioritiesSum(it) }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { getBadgeItemPriority(it) }
    }

    initPriorities()

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

val priorities: HashMap<String, Int> = HashMap()

private fun initPriorities() {
    var current = 'a'
    var priority = 1
    while (current <= 'z') {
        priorities[current.toString()] = priority
        priorities[current.uppercase()] = priority + 26
        current++
        priority++
    }
}

private fun getPriority(value: Char): Int {
    return priorities[value.toString()] ?: 0
}

fun getPrioritiesSum(value: String): Int {
    if (value.isBlank()) return 0

    val res = HashMap<Char, Int>()
    val mid = value.length / 2
    var num = 0
    value.toCharArray().forEach {
        when {
            num < mid -> res[it] = 1
            else -> if (res.contains(it)) res[it] = 2
        }
        num++
    }
    return res.sumByPriority { it == 2 }
}

fun getBadgeItemPriority(values: List<String>): Int {
    val res = HashMap<Char, Int>()
    values.map { it.toCharArray().toSet() }
        .forEach { chars ->
            chars.forEach {
                res[it] = (res[it] ?: 0) + 1
            }
        }
    return res.sumByPriority { it == values.size }
}

fun Map<Char, Int>.sumByPriority(valuesPredicate: (Int) -> Boolean): Int {
    return filterValues { valuesPredicate.invoke(it) }.keys.sumOf { getPriority(it) }
}


