package com.example.runyclub.database

enum class RunSortOrder {
    DATE,
    DURATION,
    CALORIES_BURNED,
    AVG_SPEED,
    DISTANCE;

    override fun toString(): String {
        return super.toString()
            .lowercase()
            .replace('_', ' ')
            .replaceFirstChar { it.uppercase() }
    }
}
