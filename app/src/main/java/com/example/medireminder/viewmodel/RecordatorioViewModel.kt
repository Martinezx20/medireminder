package com.example.medireminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.database.Recordatorio
import kotlinx.coroutines.launch

class RecordatorioViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).recordatorioDao()
    val recordatorios = MutableLiveData<List<Recordatorio>>()

    fun cargarRecordatorios(medId: Int) {
        viewModelScope.launch {
            recordatorios.postValue(dao.getByMedicamento(medId))
        }
    }

    fun agregarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            dao.insert(recordatorio)
            cargarRecordatorios(recordatorio.id_medicamento)
        }
    }

    fun eliminarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            dao.delete(recordatorio)
            cargarRecordatorios(recordatorio.id_medicamento)
        }
    }
}