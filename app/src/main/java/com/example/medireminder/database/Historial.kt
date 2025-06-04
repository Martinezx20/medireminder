package com.example.medireminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Historial(
    @PrimaryKey(autoGenerate = true) val id_historial: Int = 0,
    val id_usuario: Int,
    val fecha_inicio: String,
    val fecha_fin: String,
    val resumen: String
)