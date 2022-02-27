package com.robot.surface.model

import com.robot.surface.abstraction.BasicRobot
import com.robot.surface.config.ListPairInt

class SurfaceExplorer(
    override var direction: Direction,
    private var placement: Pair<Int, Int>,
    private val obstacles: ListPairInt
) : BasicRobot {

    private var iamInFrontOfObstacle = false
    private var iamInBackOfObstacle = false

    override val position: String
        get() {
            val isStopped = if (iamInFrontOfObstacle || iamInBackOfObstacle) "STOPPED" else ""
            return "(${placement.first}, ${placement.second}) $direction $isStopped".trim()
        }

    override fun goForward(): Boolean {
        val nextMove = direction.front(placement.first, placement.second)
        return goAndNotify(nextMove) { iamInFrontOfObstacle = true }
    }

    override fun goBackward(): Boolean {
        val nextMove = direction.back(placement.first, placement.second)
        return goAndNotify(nextMove) { iamInBackOfObstacle = true }
    }

    override fun turnLeft() {
        direction = direction.left()
    }

    override fun turnRight() {
        direction = direction.right()
    }

    private fun goAndNotify(nextMove: Pair<Int, Int>, discoverObstacle: () -> Unit): Boolean =
        if (nextMove in obstacles) {
            discoverObstacle()
            false
        } else {
            placement = nextMove
            true
        }

}