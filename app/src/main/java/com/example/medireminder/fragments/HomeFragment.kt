package com.example.medireminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.medireminder.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnMedicamentos).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_medicamentosFragment)
        }
        view.findViewById<Button>(R.id.btnRecordatorios).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recordatoriosFragment)
        }
        view.findViewById<Button>(R.id.btnHistorial).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historialFragment)
        }
        view.findViewById<Button>(R.id.btnSintomas).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sintomasFragment)
        }

        // Listener para el botón de cerrar sesión
        view.findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            // Aquí puedes añadir cualquier lógica de limpieza de sesión,
            // como limpiar SharedPreferences, tokens, etc.

            // Navega al LoginFragment y borra la pila de retroceso
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
}