// app/src/main/java/com/example/medireminder/database/Sintoma.kt
package com.example.medireminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sintoma(
    @PrimaryKey(autoGenerate = true) val id_sintoma: Int = 0,
    val descripcion: String,
    val intensidad: Int, // <-- ¡Cambiar de String a Int!
    val fecha: String,
    val id_medicamento: Int
)