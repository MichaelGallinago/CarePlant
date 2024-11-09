package net.micg.plantcare.data

import net.micg.plantcare.data.models.Alarm

interface AlarmsRepository {
    fun saveAlarms(alarmList: List<Alarm>)
    fun loadAlarms(): List<Alarm>
}