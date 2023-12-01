fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val numbers = it.filter { it.isDigit() }
            "${numbers.first()}${numbers.last()}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("[0-9]|one|two|three|four|five|six|seven|eight|nine")
        val digits = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
        return input.sumOf { line ->
            val parsedLine = line.replace("twone", "twoone")
                .replace("eightwo", "eighttwo")
                .replace("oneight", "oneeight")
            val matches = regex.findAll(parsedLine)
            val first = matches.first().value
            val last = matches.last().value
            "${digits[first] ?: first.toInt()}${digits[last] ?: last.toInt()}".toInt()
        }
    }

    val testInput = readInput("sample/day01")
    check(part2(testInput) == 281)
    val input = readInput("input/day01")

    part1(input).println()
    part2(input).println()
}
