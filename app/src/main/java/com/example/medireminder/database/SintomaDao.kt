package com.example.medireminder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SintomaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sintoma: Sintoma)

    @Query("SELECT * FROM Sintoma WHERE id_medicamento = :medicamentoId") // Cambiado a id_medicamento
    suspend fun getByMedicamento(medicamentoId: Int): List<Sintoma> // Nombre de la función también cambia

    @Delete
    suspend fun delete(sintoma: Sintoma)
}