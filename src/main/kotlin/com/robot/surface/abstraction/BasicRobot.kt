package com.robot.surface.abstraction

import com.robot.surface.model.Direction

interface BasicRobot {
    val position: String
    val direction: Direction
    fun goForward(): Boolean
    fun goBackward(): Boolean
    fun turnLeft()
    fun turnRight()
}