package com.example.medireminder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicamentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicamento: Medicamento)
    @Query("SELECT * FROM Medicamento WHERE id_usuario = :userId")
    suspend fun getByUser(userId: Int): List<Medicamento>
    @Delete
    suspend fun delete(medicamento: Medicamento)
}