package net.micg.plantcare.data.models.alarm

import androidx.room.*

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("SELECT * FROM alarms ORDER BY id")
    suspend fun getAllAlarms(): List<Alarm>

    @Update
    suspend fun update(alarm: Alarm)
}