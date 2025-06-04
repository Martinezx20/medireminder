package com.example.medireminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.database.Historial
import kotlinx.coroutines.launch

class HistorialViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).historialDao()
    val historiales = MutableLiveData<List<Historial>>()

    fun cargarHistorial(userId: Int) {
        viewModelScope.launch {
            historiales.postValue(dao.getByUser(userId))
        }
    }

    fun agregarHistorial(historial: Historial) {
        viewModelScope.launch {
            dao.insert(historial)
            cargarHistorial(historial.id_usuario)
        }
    }

    fun eliminarHistorial(historial: Historial) {
        viewModelScope.launch {
            dao.delete(historial)
            cargarHistorial(historial.id_usuario)
        }
    }
}