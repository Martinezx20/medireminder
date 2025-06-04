// src/main/java/com/example/medireminder/fragments/RecordatoriosFragment.kt
package com.example.medireminder.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medireminder.R
import com.example.medireminder.adapter.RecordatorioAdapter
import com.example.medireminder.database.Recordatorio
import com.example.medireminder.viewmodel.RecordatorioViewModel

class RecordatoriosFragment : Fragment(R.layout.fragment_recordatorios) {
    private lateinit var viewModel: RecordatorioViewModel
    private lateinit var recyclerView: RecyclerView
    private var loggedInUserId: Int = -1 // Aunque este fragmento usa id_medicamento, es bueno tener el userId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperar el ID del usuario (aunque no se use directamente para cargar recordatorios)
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        loggedInUserId = sharedPref?.getInt("logged_in_user_id", -1) ?: -1

        if (loggedInUserId == -1) {
            Toast.makeText(context, "Error: No se encontró ID de usuario logueado.", Toast.LENGTH_LONG).show()
            // Puedes decidir si salir o no, ya que Recordatorio no depende directamente de id_usuario
            // por ahora en tu DAO.
        }

        viewModel = ViewModelProvider(this).get(RecordatorioViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerRecordatorios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recordatorios.observe(viewLifecycleOwner) { lista ->
            recyclerView.adapter = RecordatorioAdapter(lista)
        }

        // Si RecordatorioDao.getByMedicamento(medId) sigue siendo el único método,
        // esto cargará recordatorios para el medicamento con ID 1.
        // Si quieres que dependa del usuario, necesitarías una lógica diferente.
        viewModel.cargarRecordatorios(1) // Mantenemos el 1 hardcodeado para medId por ahora

        view.findViewById<Button>(R.id.btnAgregarRecordatorio).setOnClickListener {
            // Si el recordatorio se asocia a un medicamento, y los medicamentos son por usuario,
            // esta lógica de agregar también necesitaría saber el id_medicamento correcto.
            // Por ahora, se sigue usando el 1 hardcodeado.
            val nuevo = Recordatorio(0, "08:00", "Alarma", 1) // id_medicamento 1 hardcodeado
            viewModel.agregarRecordatorio(nuevo)
        }
    }
}