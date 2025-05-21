package net.micg.plantcare.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.alarm.db.AlarmDatabase

@Module
class AlarmDatabaseModule {
    @Provides
    @AppComponentScope
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context.applicationContext, AlarmDatabase::class.java, "alarm_database"
    ).addMigrations(MIGRATION_2_3).build()

    @Provides
    @AppComponentScope
    fun provideAlarmDao(database: AlarmDatabase) = database.alarmDao()

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) = database.execSQL(
                "ALTER TABLE alarms ADD COLUMN isInCalendar INTEGER NOT NULL DEFAULT 0"
            )
        }
    }
}
