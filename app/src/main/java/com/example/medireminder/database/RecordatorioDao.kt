package com.example.medireminder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordatorioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recordatorio: Recordatorio)
    @Query("SELECT * FROM Recordatorio WHERE id_medicamento = :medId")
    suspend fun getByMedicamento(medId: Int): List<Recordatorio>
    @Delete
    suspend fun delete(recordatorio: Recordatorio)
}