package com.robot.surface.abstraction

import com.robot.surface.config.ListPairInt

interface NaviDirections<T> {
    fun getCoordinates(point: Pair<Int, Int>): ListPairInt
    fun getNextDirection(from: Pair<Int, Int>, to: Pair<Int, Int>): T?
}