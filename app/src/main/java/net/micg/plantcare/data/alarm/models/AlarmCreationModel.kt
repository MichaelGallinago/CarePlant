package net.micg.plantcare.data.alarm.models

data class AlarmCreationModel(
    val plantName: String, var interval: Int, val isWaterSprayingEnabled: Boolean = false
)
