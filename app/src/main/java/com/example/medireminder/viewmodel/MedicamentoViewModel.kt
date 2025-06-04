package com.example.medireminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.medireminder.database.AppDatabase
import com.example.medireminder.database.Medicamento
import kotlinx.coroutines.launch

class MedicamentoViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).medicamentoDao()
    val medicamentos = MutableLiveData<List<Medicamento>>()

    fun cargarMedicamentos(userId: Int) {
        viewModelScope.launch {
            medicamentos.postValue(dao.getByUser(userId))
        }
    }

    fun agregarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            dao.insert(medicamento)
            cargarMedicamentos(medicamento.id_usuario)
        }
    }

    fun eliminarMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            dao.delete(medicamento)
            cargarMedicamentos(medicamento.id_usuario)
        }
    }
}