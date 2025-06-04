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
import com.example.medireminder.adapter.MedicamentoAdapter
import com.example.medireminder.database.Medicamento
import com.example.medireminder.viewmodel.MedicamentoViewModel

class MedicamentosFragment : Fragment(R.layout.fragment_medicamentos) {
    private lateinit var viewModel: MedicamentoViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerMedicamentos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Configurar el adaptador con un click listener para futuras funcionalidades
        // Por ahora, el click listener solo mostrará un toast
        viewModel.medicamentos.observe(viewLifecycleOwner) { lista ->
            recyclerView.adapter = MedicamentoAdapter(lista) { medicamento ->
                // Acción al hacer clic en un medicamento (ej. ver detalles, editar)
                // Toast.makeText(requireContext(), "Clic en ${medicamento.nombre}", Toast.LENGTH_SHORT).show()
            }
        }
        // Cargar medicamentos del usuario actual (sustituir 1 por el ID real del usuario)
        val currentUserId = 1 // Reemplazar con el ID de usuario real
        viewModel.cargarMedicamentos(currentUserId)

        view.findViewById<Button>(R.id.btnAgregarMedicamento).setOnClickListener {
            // Navegar al fragmento para agregar un nuevo medicamento
            findNavController().navigate(R.id.action_medicamentosFragment_to_addMedicamentoFragment)
        }
    }
}