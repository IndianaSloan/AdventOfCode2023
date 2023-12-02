import Cube.*

fun main() {

    val gameRegex = Regex("Game \\d+:")
    val integerRegex = Regex("\\d+")
    
    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val gameRef = gameRegex.find(game)?.value!!
            val gameNumber = integerRegex.find(gameRef)!!.value.toInt()
            val possibleRounds = game.replace(gameRegex, "").trim().split(";").map { round ->
                round.split(", ").all { cubePlay ->
                    val count = integerRegex.find(cubePlay)!!.value.toInt()
                    when {
                        cubePlay.contains(RED.name, ignoreCase = true) -> count <= 12
                        cubePlay.contains(GREEN.name, ignoreCase = true) -> count <= 13
                        cubePlay.contains(BLUE.name, ignoreCase = true) -> count <= 14
                        else -> true
                    }
                }
            }
            if (possibleRounds.filter { it }.size == possibleRounds.size) {
                gameNumber
            } else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            val redCount = mutableListOf<Int>()
            val greenCount = mutableListOf<Int>()
            val blueCount = mutableListOf<Int>()
            game.replace(gameRegex, "").trim().split(";").forEach { round ->
                round.split(", ").all { cubePlay ->
                    val count = integerRegex.find(cubePlay)!!.value.toInt()
                    when {
                        cubePlay.contains(RED.name, ignoreCase = true) -> redCount.add(count)
                        cubePlay.contains(GREEN.name, ignoreCase = true) -> greenCount.add(count)
                        cubePlay.contains(BLUE.name, ignoreCase = true) -> blueCount.add(count)
                        else -> true
                    }
                }
            }
            redCount.max() * greenCount.max() * blueCount.max()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day02")
    check(part2(testInput) == 2286)

    val input = readInput("input/day02")
    part1(input).println()
    part2(input).println()
}

private enum class Cube {
    BLUE,
    RED,
    GREEN,
}
