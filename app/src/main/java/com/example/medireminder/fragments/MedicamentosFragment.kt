package com.example.medireminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast // Importar Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel.medicamentos.observe(viewLifecycleOwner) { lista ->
            // Aquí se pasa la función onItemClick al adaptador
            recyclerView.adapter = MedicamentoAdapter(lista) { medicamento ->
                // Aquí defines la acción a realizar cuando se hace clic en un medicamento
                // Por ejemplo, mostrar un Toast o navegar a una pantalla de detalles
                Toast.makeText(requireContext(), "Medicamento clickeado: ${medicamento.nombre}", Toast.LENGTH_SHORT).show()
                // Puedes añadir aquí la lógica de navegación, por ejemplo:
                // findNavController().navigate(R.id.action_medicamentosFragment_to_detalleMedicamentoFragment, bundleOf("medicamentoId" to medicamento.id_medicamento))
            }
        }
        viewModel.cargarMedicamentos(1)
        view.findViewById<Button>(R.id.btnAgregarMedicamento).setOnClickListener {
            val nuevo = Medicamento(0, "Paracetamol", "500mg", "Cada 8h", "Con agua", 1)
            viewModel.agregarMedicamento(nuevo)
        }
    }
}