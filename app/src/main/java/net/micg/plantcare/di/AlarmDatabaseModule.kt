package net.micg.plantcare.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.models.alarm.AlarmDao
import net.micg.plantcare.data.models.alarm.AlarmDatabase

@Module
class AlarmDatabaseModule {
    @Provides
    fun provideDatabase(context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlarmDatabase::class.java,
            "alarm_database"
        )
            .fallbackToDestructiveMigration() //TODO: REMOVE THIS
            .build()
    }

    @Provides
    fun provideAlarmDao(database: AlarmDatabase): AlarmDao = database.alarmDao()
}
