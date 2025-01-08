package net.micg.plantcare.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.alarm.db.AlarmDatabase

@Module
class AlarmDatabaseModule {
    @Provides
    @AppComponentScope
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context.applicationContext, AlarmDatabase::class.java, "alarm_database"
    ).build()

    @Provides
    @AppComponentScope
    fun provideAlarmDao(database: AlarmDatabase) = database.alarmDao()
}
