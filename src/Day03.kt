import java.awt.Point

fun main() {
    fun part1(input: List<String>): Int {
        val coordinates = mutableSetOf<Point>()
        val grid = input.toCharGrid()
        grid.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                // This is a valid symbol
                if (!char.isDigit() && char != '.') {
                    // check its surrounding box for a digit
                    ((r - 1)..(r + 1)).forEach { cr ->
                        ((c - 1)..(c + 1)).forEach { cc ->
                            if (cr >= 0 && cr < grid.size && cc >= 0 && cc < grid[cr].size && grid[cr][cc].isDigit()) {
                                // Found a digit in the surrounding box, lets find the point of its first digit.
                                var mutableCC = cc
                                while (mutableCC > 0 && grid[cr][mutableCC - 1].isDigit()) {
                                    mutableCC -= 1
                                }
                                coordinates.add(Point(cr, mutableCC))
                            }
                        }
                    }
                }
            }
        }
        val partNumbers = mutableListOf<Int>()
        // Iterate through the coordinates, and find each part number by iterating along the Y axis.
        coordinates.forEach { point -> partNumbers.add(point.findNumber(grid)) }
        return partNumbers.sum()
    }

    fun part2(input: List<String>): Int {
        val coords = mutableListOf<Pair<Point, Point>>()
        val grid = input.toCharGrid()
        grid.forEachIndexed { r, line ->
            line.forEachIndexed { c, char ->
                // We only want to find gears
                if (char == '*') {
                    val gearSet = mutableSetOf<Point>()
                    // check its surrounding box for a digit
                    ((r - 1)..(r + 1)).forEach { cr ->
                        ((c - 1)..(c + 1)).forEach { cc ->
                            if (cr >= 0 && cr < grid.size && cc >= 0 && cc < grid[cr].size && grid[cr][cc].isDigit()) {
                                // Found a digit in the surrounding box, lets find the point of its first digit.
                                var mutableCC = cc
                                while (mutableCC > 0 && grid[cr][mutableCC - 1].isDigit()) {
                                    mutableCC -= 1
                                }
                                gearSet.add(Point(cr, mutableCC))
                            }
                        }
                    }
                    if (gearSet.size == 2) {
                        coords.add(Pair(gearSet.first(), gearSet.last()))
                    }
                }
            }
        }
        // Iterate through each gear set multiplying its two part numbers together.
        val partNumbers = mutableListOf<Int>()
        coords.forEach { gearSet ->
            val first = gearSet.first.findNumber(grid)
            val second = gearSet.second.findNumber(grid)
            partNumbers.add(first * second)
        }
        return partNumbers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("sample/day03")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("input/day03")
    part1(input).println()
    part2(input).println()
}

fun Point.findNumber(grid: List<List<Char>>): Int {
    val stringBuilder = StringBuilder()
    var mutableY = y
    while (mutableY < grid[x].size && grid[x][mutableY].isDigit()) {
        stringBuilder.append(grid[x][mutableY])
        mutableY += 1
    }
    return stringBuilder.toString().toInt()
}

fun List<String>.toCharGrid() = map { line -> line.map { char -> char } }