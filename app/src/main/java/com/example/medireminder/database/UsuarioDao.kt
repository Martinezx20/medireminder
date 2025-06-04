package com.example.medireminder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)
    @Query("SELECT * FROM Usuario WHERE email = :email AND contrasena = :password")
    suspend fun login(email: String, password: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE email = :email")
    suspend fun getUserByEmail(email: String): Usuario?


}