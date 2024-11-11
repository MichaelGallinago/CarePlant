package net.micg.plantcare.data.models.alarm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)

    @Query("SELECT * FROM alarms ORDER BY id")
    fun getAllAlarms(): LiveData<List<Alarm>>

    @Update
    suspend fun update(alarm: Alarm)
}