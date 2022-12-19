fun main() {
    fun part1(input: List<String>): String {
        return process(input, CrateMover9000(input.getBucketsNumber()))
    }

    fun part2(input: List<String>): String {
        return process(input, CrateMover9001(input.getBucketsNumber()))
    }

    val testInput = readInput("Day05_test")
    val res = part2(testInput)
    check(part1(testInput) == "CMZ")
    check(res == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun process(input: List<String>, crateMover: CrateMover): String {
    val executor = Executor(getCommands(input))
    crateMover.addEntries(input)
    return executor.execute(crateMover).result()
}

fun getCommands(input: List<String>): ArrayDeque<Command> {
    val res = ArrayDeque<Command>()
    input.takeLastWhile { it.isNotBlank() }
        .forEach {
        res.addLast(Command.of(it))
    }
    return res
}

fun List<String>.getBucketsNumber(): Int {
    return takeWhile { it.isNotBlank() }.last()
        .split(' ')
        .filter { it.isNotBlank() }
        .size
}

fun String.getEntry(buckets: Int): List<String> {
    return mutableListOf<String>().apply {
        for (i in 0 until buckets) {
            val index = 1 + i * 4
            if (length > index) {
                add(this@getEntry[index].toString())
            } else {
                add("")
            }
        }
    }
}

class Command(
    val number: Int,
    val from: Int,
    val to: Int
) {
    companion object {
        fun of(input: String): Command {
            val params = input.split(' ')
            return Command(
                number = params[1].toInt(),
                from = params[3].toInt(),
                to = params[5].toInt()
            )
        }
    }
}

class Executor(
    private val commands: ArrayDeque<Command>
) {
    fun execute(crateMover: CrateMover): CrateMover {
        commands.forEach { crateMover.apply(it) }
        return crateMover
    }
}

abstract class CrateMover(
    private val bucketsNumber: Int
) {
    protected val entries: List<ArrayDeque<String>> = mutableListOf<ArrayDeque<String>>().apply {
        repeat(bucketsNumber) {
            this.add(ArrayDeque())
        }
    }

    private fun add(values: List<String>) {
        for (i in values.indices) {
            if (values[i].isNotBlank()) {
                entries[i].addLast(values[i])
            }
        }
    }

    fun addEntries(input: List<String>) {
        input.takeWhile { it.contains('[') }.forEach {
            add(it.getEntry(bucketsNumber))
        }
    }

    fun result(): String {
        return entries.joinToString("") { it.first() }
    }

    abstract fun apply(command: Command)
}

class CrateMover9000(bucketsNumber: Int) : CrateMover(bucketsNumber) {
    override fun apply(command: Command) {
        val from = entries[command.from - 1]
        val to = entries[command.to - 1]
        repeat(command.number) {  to.addFirst(from.removeFirst()) }
    }
}

class CrateMover9001(bucketsNumber: Int) : CrateMover(bucketsNumber) {
    override fun apply(command: Command) {
        val from = entries[command.from - 1]
        val to = entries[command.to - 1]
        val tmp = ArrayDeque<String>()
        repeat(command.number) {
            tmp.addLast(from.removeFirst())
        }
        for(i in tmp.indices.reversed()) {
            to.addFirst(tmp[i])
        }
    }
}