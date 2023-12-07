fun main() {

    fun part1(input: List<String>): Int {
        val values = listOf('T', 'J', 'Q', 'K', 'A')
        return input.asSequence().map { it.split(" ") }
            .map { (text, bid) ->
                val cards = text.map { card ->
                    values.indexOf(card).let { if (it > -1) it + 10 else card.digitToInt() }
                }
                val groups = cards.groupBy { it }
                    .map { it.value.size }
                    .sortedByDescending { it }
                Round(cards, groups, bid.toInt())
            }
            .sortedWith(Round.Comparator)
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val values = listOf('T', 'Q', 'K', 'A')
        return input.asSequence().map { it.split(" ") }
            .map { (text, bid) ->
                val cards = text.map { card ->
                    values.indexOf(card).let { if (it > -1) it + 10 else card.digitToIntOrNull() ?: 1 }
                }
                val possibleGroups = (2..13)
                    .map { swap ->
                        cards.map { if (it == 1) swap else it }
                            .groupBy { it }
                            .map { it.value.size }
                            .sortedByDescending { it }
                    }
                val bestGroups = possibleGroups.sortedWith(compareBy({ it[0] }, { it.getOrNull(1) })).last()
                Round(cards, bestGroups, bid.toInt())
            }
            .sortedWith(Round.Comparator)
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day07")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("input/day07")
    part1(input).println()
    part2(input).println()
}

class Round(
    val cards: List<Int>,
    val groupCounts: List<Int>,
    val bid: Int,
) {
    companion object {
        val Comparator = compareBy<Round>(
            { round -> round.groupCounts[0] },
            { round -> round.groupCounts.getOrNull(1) },
            { round -> round.cards[0] },
            { round -> round.cards[1] },
            { round -> round.cards[2] },
            { round -> round.cards[3] },
            { round -> round.cards[4] }
        )
    }
}