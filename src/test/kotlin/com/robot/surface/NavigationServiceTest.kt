package com.robot.surface

import com.robot.surface.model.Direction
import com.robot.surface.model.Direction.EAST
import com.robot.surface.model.Direction.WEST
import com.robot.surface.model.Direction.SOUTH
import com.robot.surface.model.Direction.NORTH
import com.robot.surface.service.NavigationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NavigationServiceTest {

    private val navigationService = NavigationService()

//     7  - - - x x x e
//     6  - - - x - x -
//     5  - - - x - X -
//     4  - - - x - - -
//     3  - - - x - x -
//     2  - x x x - x -
//     1  - s - - - x -
//     0  - - - - - x -
//        0 1 2 3 4 5 6
    @Test
    fun `shout go through first route`() {
        // GIVEN
        val start = Pair(1, 1)
        val end = Pair(6, 7)
        val obstacles = listOf(
            Pair(3, 7), Pair(4, 7), Pair(5, 7),
            Pair(3, 6), Pair(5, 6),
            Pair(3, 5), Pair(5, 5),
            Pair(3, 4),
            Pair(3, 3), Pair(5, 3),
            Pair(1, 2), Pair(2, 2), Pair(3, 2), Pair(5, 2),
            Pair(5, 1),
            Pair(5, 0)
        )
        // WHEN
        val coordinates = navigationService.getCoordinates(SOUTH, start, end, obstacles)
        val expectedRoute = listOf(EAST, EAST, EAST, NORTH, NORTH, NORTH, EAST, EAST, NORTH, NORTH, NORTH)
        // THEN
        assertEquals(coordinates, expectedRoute)
    }

//     6  - x x x x x x x
//     5  - x - - - - - x
//     4  x x - x x x e -
//     3  x - - x - x - -
//     2  x - x x - x - -
//     1  - - s - - x - -
//     0  x x x x x x - -
//        0 1 2 3 4 5 6 7
    @Test
    fun `shout go through second route`() {
        // GIVEN
        val start = Pair(2, 1)
        val end = Pair(6, 4)
        val obstacles = listOf(
            Pair(1, 6), Pair(2, 6), Pair(3, 6), Pair(4, 6), Pair(5, 6), Pair(6, 6), Pair(7, 6),
            Pair(1, 5), Pair(7, 5),
            Pair(0, 4), Pair(1, 4), Pair(3, 4), Pair(4, 4), Pair(5, 4),
            Pair(0, 3), Pair(3, 3), Pair(5, 3),
            Pair(0, 2), Pair(2, 2), Pair(3, 2), Pair(5, 2),
            Pair(5, 1),
            Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0), Pair(5, 0)
        )
        // WHEN
        val coordinates = navigationService.getCoordinates(NORTH, start, end, obstacles)
        val expectedRoute = listOf(WEST, NORTH, NORTH, EAST, NORTH, NORTH, EAST, EAST, EAST, EAST, SOUTH)
        // THEN
        assertEquals(coordinates, expectedRoute)
    }

//     4  - - - x x x e
//     3  - - - x - x -
//     2  - x x x - x -
//     1  - x s - - x -
//     0  - x x x x x -
//        0 1 2 3 4 5 6
    @Test
    fun `shout discover dead end`() {
        // GIVEN
        val start = Pair(2, 1)
        val end = Pair(6, 4)
        val obstacles = listOf(
            Pair(3, 4), Pair(4, 4), Pair(5, 4),
            Pair(3, 3), Pair(5, 3),
            Pair(1, 2), Pair(2, 2), Pair(3, 2), Pair(5, 2),
            Pair(1, 1), Pair(5, 1),
            Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0), Pair(5, 0)
        )
        // WHEN
        val coordinates = navigationService.getCoordinates(EAST, start, end, obstacles)
        val expectedRoute = emptyList<Direction>()
        // THEN
        assertEquals(coordinates, expectedRoute)
    }

//     2  x x x e
//     1  x s x -
//     0  x x x -
//        0 1 2 3
    @Test
    fun `shout discover no way to go`() {
        // GIVEN
        val start = Pair(1, 1)
        val end = Pair(3, 2)
        val obstacles = listOf(
            Pair(0, 2), Pair(1, 2), Pair(2, 2),
            Pair(0, 1), Pair(2, 1),
            Pair(0, 0), Pair(1, 0), Pair(2, 0),
        )
        // WHEN
        val coordinates = navigationService.getCoordinates(WEST, start, end, obstacles)
        val expectedRoute = emptyList<Direction>()
        // THEN
        assertEquals(coordinates, expectedRoute)
    }

}