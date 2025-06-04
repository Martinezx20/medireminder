package com.example.medireminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicamento(
    @PrimaryKey(autoGenerate = true) val id_medicamento: Int = 0,
    val nombre: String,
    val dosis: String,
    val frecuencia: String,
    val instrucciones: String,
    val id_usuario: Int
)