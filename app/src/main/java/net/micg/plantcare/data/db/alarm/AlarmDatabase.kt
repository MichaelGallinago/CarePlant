package net.micg.plantcare.data.db.alarm

import androidx.room.Database
import androidx.room.RoomDatabase
import net.micg.plantcare.data.models.alarm.AlarmEntity

@Database(entities = [AlarmEntity::class], version = 2, exportSchema = false)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
