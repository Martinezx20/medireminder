package com.example.medireminder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistorialDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historial: Historial)
    @Query("SELECT * FROM Historial WHERE id_usuario = :userId")
    suspend fun getByUser(userId: Int): List<Historial>
    @Delete
    suspend fun delete(historial: Historial)
}