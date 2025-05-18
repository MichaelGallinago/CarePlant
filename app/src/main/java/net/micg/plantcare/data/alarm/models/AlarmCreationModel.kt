package net.micg.plantcare.data.alarm.models

data class AlarmCreationModel(
    val plantName: String,
    var interval: Int,
    val fertilizingInterval: Int = 1,
    val waterSprayingInterval: Int = 0
)
