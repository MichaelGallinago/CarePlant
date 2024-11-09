package net.micg.plantcare.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.models.AlarmDao
import net.micg.plantcare.data.models.AlarmDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AlarmDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlarmDatabase::class.java,
            "alarm_database"
        ).build()
    }

    @Provides
    fun provideAlarmDao(database: AlarmDatabase): AlarmDao = database.alarmDao()
}