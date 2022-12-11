fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { apply(it, RPS::fight) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { apply(it, RPS::getByResult) }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class RPS(
    val title: String,
    private val points: Int,
    private val other: String,
    private val wins: String
) {
    X("Rock", 1, "A", "C"),
    Y("Paper", 2, "B", "A"),
    Z("Scissors", 3, "C", "B");

    companion object {
        fun fight(other: String, value: String): Int {
            val rps = RPS.valueOf(value)
            val result =  when {
                rps.wins == other -> Result.WIN
                rps.other == other -> Result.DRAW
                else -> Result.LOSS
            }
            return result.points + rps.points
        }

        fun getByResult(other: String, resultString: String): Int {
            val rps = RPS.getByOther(other)
            val result = Result.of(resultString)
            val value = when (result) {
                Result.DRAW -> rps
                Result.WIN -> values().first { it.wins == rps.other }
                else -> values().first { it.other == rps.wins }
            }
            return value.points + result.points
        }

        private fun getByOther(other: String): RPS {
            return values().first { it.other == other }
        }
    }
}

enum class Result(val points: Int, private val stringValue: String) {
    WIN(6, "Z"),
    DRAW(3, "Y"),
    LOSS(0, "X");

    companion object {
        fun of(value: String): Result {
            return values().first { it.stringValue == value }
        }
    }
}

fun apply(input: String, transform: (first: String, second: String) -> Int): Int {
    val (first, second) = input.split(" ")
    return transform.invoke(first, second)
}