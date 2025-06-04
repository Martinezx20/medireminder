package com.example.medireminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recordatorio(
    @PrimaryKey(autoGenerate = true) val id_recordatorio: Int = 0,
    val hora: String,
    val tipo_alerta: String,
    val id_medicamento: Int
)