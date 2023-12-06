fun main() {
    val digitRegex = Regex("\\s++")

    fun parseLine(line: String): List<Int> {
        return line.substringAfter(": ")
            .split(digitRegex)
            .filter { it.isNotEmpty() }
            .map { it.trim().toInt() }
    }

    fun countPossibleWins(timeAllowed: Long, distanceRequired: Long): Int {
        return (timeAllowed downTo 0).mapNotNull { accelDuration ->
            val fastTime = timeAllowed - accelDuration
            val remainingTime = timeAllowed - accelDuration - fastTime
            val distance = (fastTime * accelDuration) + (remainingTime)
            if (distance > distanceRequired) Unit else null
        }.count()
    }

    fun part1(input: List<String>): Int {
        val (timeLine, distanceLine) = input
        val times = parseLine(timeLine).map(Int::toLong)
        val distances = parseLine(distanceLine).map(Int::toLong)
        val counts = times.mapIndexed { index, timeAllowed -> countPossibleWins(timeAllowed, distances[index]) }
        return counts.fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val (timeLine, distanceLine) = input
        val timeAllowed = parseLine(timeLine).joinToString(separator = "").toLong()
        val distanceRequired = parseLine(distanceLine).joinToString(separator = "").toLong()
        return countPossibleWins(timeAllowed, distanceRequired)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day06")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("input/day06")
    part1(input).println()
    part2(input).println()
}
