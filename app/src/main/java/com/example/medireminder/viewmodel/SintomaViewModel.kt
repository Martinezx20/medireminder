package com.example.medireminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.database.Sintoma
import kotlinx.coroutines.launch

class SintomaViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).sintomaDao()
    val sintomas = MutableLiveData<List<Sintoma>>()

    fun cargarSintomas(medicamentoId: Int) { // Cambiado el parámetro
        viewModelScope.launch {
            sintomas.postValue(dao.getByMedicamento(medicamentoId)) // Llamada a la nueva función
        }
    }

    fun agregarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            dao.insert(sintoma)
            cargarSintomas(sintoma.id_medicamento) // Usar id_medicamento
        }
    }

    fun eliminarSintoma(sintoma: Sintoma) {
        viewModelScope.launch {
            dao.delete(sintoma)
            cargarSintomas(sintoma.id_medicamento) // Usar id_medicamento
        }
    }
}