fun main() {
    fun part1(input: List<String>): Int {
        return getMaxSum(input, 1)
    }

    fun part2(input: List<String>): Int {
        return getMaxSum(input, 3)
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun getMaxSum(input: List<String>, entries: Int): Int {
    if (input.isEmpty()) return 0
    val result = ArrayList<Int>()
    var current = 0
    input.forEach {
        if (it.isBlank()) {
            result.add(current)
            current = 0
        } else {
            current += it.toInt()
        }
    }

    if (current != 0)
        result.add(current)

    result.sort()
    return when {
        result.isEmpty() -> 0
        else -> result.takeLast(minOf(result.size, entries)).sum()
    }
}
