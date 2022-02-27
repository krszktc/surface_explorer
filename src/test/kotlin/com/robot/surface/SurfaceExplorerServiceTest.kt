package com.robot.surface

import com.robot.surface.model.Direction.EAST
import com.robot.surface.model.Direction.WEST
import com.robot.surface.model.Direction.SOUTH
import com.robot.surface.model.Direction.NORTH
import com.robot.surface.service.SurfaceExplorerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SurfaceExplorerServiceTest {

    private val surfaceExplorerService = SurfaceExplorerService()

    @Test
    fun `should successfully execute first command`() {
        // GIVEN
        val moveCommand = "FLFFFRFLB"
        val robot = surfaceExplorerService.putOnTheGround(4, 2, EAST)
        // WHEN
        surfaceExplorerService.move(moveCommand, robot)
        // THEN
        assertThat(robot.position).isEqualTo("(6, 4) NORTH")
    }

    @Test
    fun `should successfully execute second command`() {
        // GIVEN
        val moveCommand = "FFBBLFFRFF"
        val robot = surfaceExplorerService.putOnTheGround(0, 0, WEST)
        // WHEN
        surfaceExplorerService.move(moveCommand, robot)
        // THEN
        assertThat(robot.position).isEqualTo("(-2, -2) WEST")
    }

    @Test
    fun `should successfully execute third command`() {
        // GIVEN
        val moveCommand = "FFFFFRBBBLFF"
        val robot = surfaceExplorerService.putOnTheGround(5, 5, SOUTH)
        // WHEN
        surfaceExplorerService.move(moveCommand, robot)
        // THEN
        assertThat(robot.position).isEqualTo("(8, -2) SOUTH")
    }

    @Test
    fun `should stop robot on latest correct position`() {
        // GIVEN
        val moveCommand = "BFXFF"
        val robot = surfaceExplorerService.putOnTheGround(1, 1, NORTH)
        // WHEN
        val exc: IllegalArgumentException = assertThrows { surfaceExplorerService.move(moveCommand, robot) }
        // THEN
        assertThat(exc.message).isEqualTo("Ups... command X doesn't exist. Current position is: (1, 1) NORTH")
    }

    @Test
    fun `should report obstacle`() {
        // GIVEN
        val moveCommand = "FFFFFFFFF"
        val obstacles = listOf(Pair(1, 4), Pair(3, 5), Pair(7, 4), Pair(6, 5))
        val robot = surfaceExplorerService.putOnTheGround(1, 1, NORTH, obstacles)
        // WHEN
        surfaceExplorerService.move(moveCommand, robot)
        // THEN
        assertThat(robot.position).isEqualTo("(1, 3) NORTH STOPPED")
    }

    @Test
    fun `should execute commands partially`() {
        // GIVEN
        val moveCommand = "FFFFFF"
        val obstacles = listOf(Pair(4, 1), Pair(3, 5))
        val robot = surfaceExplorerService.putOnTheGround(1, 1, EAST, obstacles)
        // WHEN
        val processedCommand = surfaceExplorerService.move(moveCommand, robot)
        // THEN
        assertThat(processedCommand).isEqualTo("FF")
    }

    @Test
    fun `should successfully navigate to first direction`() {
        // GIVEN
        val robot = surfaceExplorerService.putOnTheGround(1, 1, EAST)
        val directions = listOf(NORTH, NORTH, EAST, EAST, NORTH, WEST, NORTH)
        // WHEN
        val moveCommand = surfaceExplorerService.navigate(directions, robot)
        // THEN
        assertEquals("LFFRFFLFLFRF", moveCommand)
    }

    @Test
    fun `should successfully navigate to second direction`() {
        // GIVEN
        val robot = surfaceExplorerService.putOnTheGround(2, 2, WEST)
        val directions = listOf(EAST, EAST, SOUTH, SOUTH, SOUTH, EAST, EAST, NORTH, NORTH)
        // WHEN
        val moveCommand = surfaceExplorerService.navigate(directions, robot)
        // THEN
        assertEquals("RRFFRFFFLFFLFF", moveCommand)
    }

    @Test
    fun `should successfully navigate to third direction`() {
        // GIVEN
        val robot = surfaceExplorerService.putOnTheGround(3, 3, NORTH)
        val directions = listOf(NORTH, NORTH, NORTH, EAST, EAST, EAST, SOUTH, WEST)
        // WHEN
        val moveCommand = surfaceExplorerService.navigate(directions, robot)
        // THEN
        assertEquals("FFFRFFFRFRF", moveCommand)
    }

}
