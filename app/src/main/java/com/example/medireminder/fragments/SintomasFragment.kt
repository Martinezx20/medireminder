// src/main/java/com/example/medireminder/fragments/SintomasFragment.kt
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
import com.example.medireminder.adapter.SintomaAdapter
import com.example.medireminder.database.Sintoma
import com.example.medireminder.viewmodel.SintomaViewModel

class SintomasFragment : Fragment(R.layout.fragment_sintomas) {
    private lateinit var viewModel: SintomaViewModel
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
            // Puedes decidir si salir o no, ya que Sintoma no depende directamente de id_usuario
            // por ahora en tu DAO.
        }

        viewModel = ViewModelProvider(this).get(SintomaViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerSintomas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.sintomas.observe(viewLifecycleOwner) { lista ->
            recyclerView.adapter = SintomaAdapter(lista)
        }

        // Carga los síntomas usando el ID de la dosis (todavía hardcodeado)
        viewModel.cargarSintomas(1) // Mantenemos el 1 hardcodeado para dosisId por ahora

        view.findViewById<Button>(R.id.btnAgregarSintoma).setOnClickListener {
            // Asegúrate de usar el loggedInUserId al agregar nuevos datos si Sintoma tuviera id_usuario
            // Por ahora, se sigue usando el 1 hardcodeado para id_dosis
            val nuevo = Sintoma(0, "Dolor de cabeza", "Alta", "2025-06-01", 1) // id_dosis 1 hardcodeado
            viewModel.agregarSintoma(nuevo)
        }
    }
}