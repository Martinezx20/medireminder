package com.example.medireminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medireminder.R
import com.example.medireminder.adapter.RecordatorioAdapter
import com.example.medireminder.database.Recordatorio
import com.example.medireminder.viewmodel.RecordatorioViewModel

class RecordatoriosFragment : Fragment(R.layout.fragment_recordatorios) {
    private lateinit var viewModel: RecordatorioViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecordatorioViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerRecordatorios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recordatorios.observe(viewLifecycleOwner) { lista ->
            recyclerView.adapter = RecordatorioAdapter(lista)
        }
        // Cargar recordatorios asociados a un medicamento (sustituir 1 por el ID real del medicamento)
        // Nota: Si quieres ver todos los recordatorios, RecordatorioDao necesitaría una función para obtenerlos todos.
        // Aquí asumimos que RecordatoriosFragment lista recordatorios asociados a un medicamento específico,
        // o necesitas ajustar la lógica para mostrar recordatorios globales al usuario.
        // Por ahora, usamos un ID de medicamento de ejemplo (1).
        val currentMedId = 1 // Reemplazar con el ID de medicamento real, o ajustar para recordatorios de usuario
        viewModel.cargarRecordatorios(currentMedId)


        view.findViewById<Button>(R.id.btnAgregarRecordatorio).setOnClickListener {
            // Navegar al fragmento para agregar un nuevo recordatorio
            findNavController().navigate(R.id.action_recordatoriosFragment_to_addRecordatorioFragment)
        }
    }
}