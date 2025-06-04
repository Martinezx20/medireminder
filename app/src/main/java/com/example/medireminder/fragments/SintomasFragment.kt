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
import com.example.medireminder.adapter.SintomaAdapter
import com.example.medireminder.database.Sintoma
import com.example.medireminder.viewmodel.SintomaViewModel

class SintomasFragment : Fragment(R.layout.fragment_sintomas) {
    private lateinit var viewModel: SintomaViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SintomaViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerSintomas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.sintomas.observe(viewLifecycleOwner) { lista ->
            recyclerView.adapter = SintomaAdapter(lista)
        }
        // Cargar síntomas asociados a una dosis (sustituir 1 por el ID real de la dosis/medicamento)
        // Similar a recordatorios, si `id_dosis` se refiere a `id_medicamento`, usar el ID del medicamento.
        // Si necesitas una vista global de síntomas del usuario, ajusta la DAO y el ViewModel.
        val currentDosisId = 1 // Reemplazar con el ID de dosis/medicamento real
        viewModel.cargarSintomas(currentDosisId)

        view.findViewById<Button>(R.id.btnAgregarSintoma).setOnClickListener {
            // Navegar al fragmento para agregar un nuevo síntoma
            findNavController().navigate(R.id.action_sintomasFragment_to_addSintomaFragment)
        }
    }
}