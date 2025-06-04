// src/main/java/com/example/medireminder/fragments/HistorialFragment.kt
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
import com.example.medireminder.adapter.HistorialAdapter
import com.example.medireminder.database.Historial
import com.example.medireminder.viewmodel.HistorialViewModel

class HistorialFragment : Fragment(R.layout.fragment_historial) {
    private lateinit var viewModel: HistorialViewModel
    private lateinit var recyclerView: RecyclerView
    private var loggedInUserId: Int = -1 // Variable para almacenar el ID del usuario

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // **** RECUPERAR EL ID DEL USUARIO DESDE SHARED PREFERENCES ****
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        loggedInUserId = sharedPref?.getInt("logged_in_user_id", -1) ?: -1

        if (loggedInUserId == -1) {
            Toast.makeText(context, "Error: No se encontró ID de usuario logueado.", Toast.LENGTH_LONG).show()
            // Si no se encuentra el ID, podrías redirigir al login o deshabilitar funcionalidades
            // findNavController().navigate(R.id.action_historialFragment_to_loginFragment)
            return // Salir del método si no hay ID válido
        }

        viewModel = ViewModelProvider(this).get(HistorialViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerHistorial)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.historiales.observe(viewLifecycleOwner) { lista ->
            recyclerView.adapter = HistorialAdapter(lista)
        }

        // Carga el historial usando el ID del usuario logueado
        viewModel.cargarHistorial(loggedInUserId)

        view.findViewById<Button>(R.id.btnAgregarHistorial).setOnClickListener {
            // Asegúrate de usar el loggedInUserId al agregar nuevos datos
            val nuevo = Historial(0, loggedInUserId, "2025-06-01", "2025-06-02", "Resumen ejemplo")
            viewModel.agregarHistorial(nuevo)
        }
    }
}