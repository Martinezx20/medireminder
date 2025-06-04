package com.example.medireminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.database.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).usuarioDao()
    val usuario = MutableLiveData<Usuario?>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            usuario.postValue(dao.login(email, password))
        }
    }

    fun registrar(usuario: Usuario) {
        viewModelScope.launch {
            dao.insert(usuario)
        }
    }
}