package com.robot.surface.abstraction

interface FourDirections<T> {
    fun front(x: Int, y: Int): Pair<Int, Int>
    fun back(x: Int, y: Int): Pair<Int, Int>
    fun left(): T
    fun right(): T
}