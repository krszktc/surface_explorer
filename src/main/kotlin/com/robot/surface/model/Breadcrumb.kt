package com.robot.surface.model

data class Breadcrumb(
    val coordinates: Pair<Int, Int>
) {
    var isImportant: Boolean = true
}