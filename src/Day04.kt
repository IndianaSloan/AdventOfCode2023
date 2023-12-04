fun main() {
    fun part1(input: List<String>): Int {
        val score = input.sumOf { round ->
            val winCount = countWinningNumbers(round)
            if (winCount > 0) {
                val score = (1..<winCount).fold(1) { acc, _ -> acc * 2 }
                score
            } else {
                0
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        val counts = IntArray(input.size) { 1 }
        input.forEachIndexed { index, round ->
            val winCount = countWinningNumbers(round)
            repeat(winCount) { winIndex ->
                counts[index + 1 + winIndex] += counts[index]
            }
        }
        return counts.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day04")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("input/day04")
    part1(input).println()
    part2(input).println()
}

private fun countWinningNumbers(round: String): Int {
    val (win, mine) = round.substringAfter(": ").split(" | ")
    val winNum = win.windowed(2, 3)
    val myNum = mine.windowed(2, 3)
    return myNum.count { it in winNum }
}
