fun main() {
    fun part1(input: List<String>): Int {
        return input.map { toSections(it) }.filter { it.fullyCovered() }.size
    }

    fun part2(input: List<String>): Int {
        return input.map { toSections(it) }.filter { it.intersected() }.size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun toSections(input: String): Pair<Section, Section> {
    val (first, second) = input.split(',').map { Section.of(it) }
    return Pair(first, second)
}

fun Pair<Section, Section>.fullyCovered(): Boolean {
    return first.contains(second) || second.contains(first)
}

fun Pair<Section, Section>.intersected(): Boolean {
    return first.intersect(second)
}

class Section(
    private val start: Int,
    private val end: Int
) {
    fun intersect(other: Section): Boolean {
        return start <= other.end && end >= other.start
    }

    fun contains(other: Section): Boolean {
        return start <= other.start && end >= other.end
    }

    companion object {
        fun of(input: String): Section {
            val (first, second) = input.split('-')
            return Section(first.toInt(), second.toInt())
        }
    }
}