import utils.leastCommonMultiple

const val START = "AAA"
const val END = "ZZZ"
fun main() {


    fun part1(input: List<String>): Int {
        val instructions = input.first().toList()

        val map = input.drop(2).associate { line ->
            val point = line.take(3)
            val (left, right) = line.substringAfter("(").dropLast(1).split(", ")
            point to Pair(left, right)
        }

        var count = 0
        var current = START
        while(current != END) {
            instructions.forEach { direction ->
                count += 1
                val step = map[current]!!
                current = if (direction == 'L') step.first else step.second
            }
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val instructions = input.first().toList()

        val map = input.drop(2).associate { line ->
            val point = line.take(3)
            val (left, right) = line.substringAfter("(").dropLast(1).split(", ")
            point to Pair(left, right)
        }

        val counts = map.keys.filter { it.endsWith("A") }.map { start ->
            var count = 0L
            var current = start
            while(!current.endsWith('Z')) {
                instructions.forEach { direction ->
                    count += 1
                    val step = map[current]!!
                    current = if (direction == 'L') step.first else step.second
                }
            }
            count
        }
        return counts.reduce { acc, i -> leastCommonMultiple(acc, i) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day08")
    check(part2(testInput) == 6L)

    val input = readInput("input/day08")
    part1(input).println()
    part2(input).println()
}
