package com.example.medireminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class, Medicamento::class, Recordatorio::class, Historial::class, Sintoma::class], version = 3) // <-- Asegúrate de que la versión esté actualizada (ej. a 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun medicamentoDao(): MedicamentoDao
    abstract fun recordatorioDao(): RecordatorioDao
    abstract fun historialDao(): HistorialDao
    abstract fun sintomaDao(): SintomaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medireminder_db"
                )
                    .fallbackToDestructiveMigration() // <-- ¡Esta línea es la que permite "destruir" y recrear!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}