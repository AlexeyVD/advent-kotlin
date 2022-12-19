fun main() {
    fun part1(input: List<String>): Int {
        return getSequenceStart(input.first(), 4)
    }

    fun part2(input: List<String>): Int {
        return getSequenceStart(input.first(), 14)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun getSequenceStart(input: String, keyLength: Int): Int {
    val tmp = mutableMapOf<Char, Int>()
    for (i in input.indices) {
        when {
            i < keyLength -> tmp.increment(input[i])
            else -> {
                tmp.increment(input[i])
                tmp.decrement(input[i - keyLength])
            }
        }
        if (tmp.size == keyLength) {
            return i + 1
        }
    }
    return input.length
}

fun MutableMap<Char, Int>.increment(key: Char) {
    put(key, (get(key) ?: 0) + 1)
}

fun MutableMap<Char, Int>.decrement(key: Char) {
    val num = get(key) ?: return
    when {
        num <= 1 -> remove(key)
        else -> put(key, num - 1)
    }
}