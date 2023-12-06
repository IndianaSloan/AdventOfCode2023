fun main() {

    fun getSeeds(input: List<String>): List<Long> = input.first()
        .substringAfter("seeds: ")
        .split(" ")
        .map(String::toLong)

    fun getSeedRanges(input: List<String>): List<LongRange> = input.first()
        .substringAfter("seeds: ")
        .split(" ")
        .map(String::toLong)
        .chunked(2)
        .map { it.first()..<(it.first() + it.last()) }

    fun getDataMaps(input: List<String>): List<Map<LongRange, LongRange>> = input.drop(2)
        .joinToString("\n")
        .split("\n\n")
        .map { group ->
            group.lines()
                .drop(1)
                .associate { line ->
                    line.split(" ")
                        .map(String::toLong)
                        .let { (dest, source, length) ->
                            source..(source + length) to dest..(dest + length)
                        }
                }
        }

    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input)
        val maps = getDataMaps(input)
        return seeds.minOf { seed ->
            maps.fold(seed) { acc, map ->
                map.entries
                    .firstOrNull { acc in it.key }
                    ?.let { (source, dest) -> dest.first + (acc - source.first) }
                    ?: acc
            }
        }
    }

    fun part2(input: List<String>): Long {
        val seedRanges = getSeedRanges(input)
        val maps = getDataMaps(input)

        return seedRanges.flatMap { seedsRange ->
            maps.fold(listOf(seedsRange)) { aac, map ->
                aac.flatMap { mapOutputs(map, it) }
            }
        }.minOf { it.first }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day05")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("input/day05")
    part1(input).println()
    part2(input).println()
}

private fun mapOutputs(map: Map<LongRange, LongRange>, input: LongRange): List<LongRange> {
    val filteredInputRanges = mutableListOf<LongRange>()
    val output = mutableListOf<LongRange>()
    map.forEach { (source, dest) ->
        val start = maxOf(source.first, input.first)
        val end = minOf(source.last, input.last)
        if (start <= end) {
            filteredInputRanges += start..end
            output.add(
                (dest.first - source.first).let { (start + it)..(end + it) }
            )
        }
    }
    val cuts = listOf(input.first) + filteredInputRanges.flatMap { listOf(it.first, it.last) } + listOf(input.last)
    val unmappedInputRanges = cuts.chunked(2).mapNotNull { (first, second) ->
        if (second > first) if (second == cuts.last()) first..second else first..<second else null
    }
    return output + unmappedInputRanges
}