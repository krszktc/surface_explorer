package com.robot.surface.model

import com.robot.surface.abstraction.FourDirections
import com.robot.surface.abstraction.NaviDirections
import com.robot.surface.abstraction.RoundVector

enum class Direction(override val weight: Int) : RoundVector, FourDirections<Direction>, NaviDirections<Direction> {

    NORTH(1) {
        override fun front(x: Int, y: Int) = Pair(x, y + 1)
        override fun back(x: Int, y: Int) = Pair(x, y - 1)
        override fun right() = EAST
        override fun left() = WEST
    },
    EAST(2) {
        override fun front(x: Int, y: Int) = Pair(x + 1, y)
        override fun back(x: Int, y: Int) = Pair(x - 1, y)
        override fun right() = SOUTH
        override fun left() = NORTH
    },
    SOUTH(3) {
        override fun front(x: Int, y: Int) = Pair(x, y - 1)
        override fun back(x: Int, y: Int) = Pair(x, y + 1)
        override fun right() = WEST
        override fun left() = EAST
    },
    WEST(4) {
        override fun front(x: Int, y: Int) = Pair(x - 1, y)
        override fun back(x: Int, y: Int) = Pair(x + 1, y)
        override fun right() = NORTH
        override fun left() = SOUTH
    };

    companion object {
        val maxWeight: Int = values().maxOf { it.weight }
    }

    override fun getCoordinates(point: Pair<Int, Int>) =
        values().map { it.front(point.first, point.second) }

    override fun getNextDirection(from: Pair<Int, Int>, to: Pair<Int, Int>) =
        values().firstOrNull { it.front(from.first, from.second) == to }
}
