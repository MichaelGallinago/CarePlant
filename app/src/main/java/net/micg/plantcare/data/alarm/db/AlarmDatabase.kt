package net.micg.plantcare.data.alarm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.micg.plantcare.data.alarm.models.AlarmEntity

@Database(entities = [AlarmEntity::class], version = 3, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
