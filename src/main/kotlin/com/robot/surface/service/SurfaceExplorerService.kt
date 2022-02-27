package com.robot.surface.service

import com.robot.surface.abstraction.BasicRobot
import com.robot.surface.config.MoveSigns.BACKWARD_SIGN
import com.robot.surface.config.MoveSigns.FORWARD_SIGN
import com.robot.surface.config.MoveSigns.LEFT_SIGN
import com.robot.surface.config.MoveSigns.RIGHT_SIGN
import com.robot.surface.config.ListPairInt
import com.robot.surface.model.Direction
import com.robot.surface.model.SurfaceExplorer

class SurfaceExplorerService {

    fun putOnTheGround(x: Int, y: Int, moveEngine: Direction, obstacles: ListPairInt = emptyList()): BasicRobot =
        SurfaceExplorer(moveEngine, Pair(x, y), obstacles)

    fun move(moveCommand: String, robot: BasicRobot): String {
        val executedCommands = mutableListOf<Char>()
        moveCommand.forEach {
            when (it) {
                BACKWARD_SIGN ->
                    if (robot.goBackward()) executedCommands.add(it)
                    else return@forEach
                FORWARD_SIGN ->
                    if (robot.goForward()) executedCommands.add(it)
                    else return@forEach
                RIGHT_SIGN -> {
                    executedCommands.add(it)
                    robot.turnRight()
                }
                LEFT_SIGN -> {
                    executedCommands.add(it)
                    robot.turnLeft()
                }
                else -> throw IllegalArgumentException(
                    "Ups... command $it doesn't exist. Current position is: ${robot.position}"
                )
            }
        }
        return executedCommands.joinToString("")
    }

    fun navigate(directions: List<Direction>, robot: BasicRobot): String {
        val moves = mutableListOf<Char>()
        listOf(robot.direction)
            .plus(directions)
            .windowed(2)
            .forEach {
                val position = it[0]
                val nextStep = it[1]
                if (position != nextStep) {
                    moves.addAll(getShortestRotation(position, nextStep))
                }
                moves.add(FORWARD_SIGN)
            }
        return move(moves.joinToString(""), robot)
    }

    private fun getShortestRotation(from: Direction, to: Direction): List<Char> {
        val rightRotationVector: Int
        val leftRotationVector: Int
        if (to > from) {
            rightRotationVector = to.wage - from.wage
            leftRotationVector = Direction.maxWage + from.wage - to.wage
        } else {
            leftRotationVector = from.wage - to.wage
            rightRotationVector = Direction.maxWage + to.wage - from.wage
        }
        return if(leftRotationVector < rightRotationVector)
            (1..leftRotationVector).map { LEFT_SIGN }
        else
            (1..rightRotationVector).map { RIGHT_SIGN }
    }

}