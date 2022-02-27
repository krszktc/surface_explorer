package com.robot.surface.service

import com.robot.surface.abstraction.NaviDirections
import com.robot.surface.config.ListPairInt
import com.robot.surface.model.Breadcrumb
import kotlin.math.abs

class NavigationService {

    fun <T> getCoordinates(
        engine: NaviDirections<T>,
        start: Pair<Int, Int>,
        target: Pair<Int, Int>,
        obstacles: ListPairInt
    ): List<T> {
        var moveIndex = 0
        val route = mutableListOf(Breadcrumb(start))
        do {
            val nextStep = engine.getCoordinates(route[moveIndex].coordinates)
                .filter { !obstacles.contains(it) && !route.contains(Breadcrumb(it)) }
                .minByOrNull { abs(target.first - it.first) + abs(target.second - it.second) }
            if (nextStep != null) {
                route.add(Breadcrumb(nextStep))
                moveIndex = route.lastIndex
            } else {
                route[moveIndex].isImportant = false
                moveIndex -= 1
            }
        } while (nextStep != target && moveIndex >= 0)

        return route
            .filter { it.isImportant }
            .windowed(2)
            .mapNotNull { engine.getNextDirection(it[0].coordinates, it[1].coordinates) }
    }

}