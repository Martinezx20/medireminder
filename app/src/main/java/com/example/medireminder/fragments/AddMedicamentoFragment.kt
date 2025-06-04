package com.example.medireminder.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medireminder.R
import com.example.medireminder.database.Medicamento
import com.example.medireminder.viewmodel.MedicamentoViewModel

class AddMedicamentoFragment : Fragment(R.layout.fragment_add_medicamento) {

    private lateinit var viewModel: MedicamentoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        val etNombre = view.findViewById<EditText>(R.id.etNombreMedicamento)
        val etDosis = view.findViewById<EditText>(R.id.etDosisMedicamento)
        val etFrecuencia = view.findViewById<EditText>(R.id.etFrecuenciaMedicamento)
        val etInstrucciones = view.findViewById<EditText>(R.id.etInstruccionesMedicamento)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarMedicamento)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val dosis = etDosis.text.toString().trim()
            val frecuencia = etFrecuencia.text.toString().trim()
            val instrucciones = etInstrucciones.text.toString().trim()

            if (nombre.isEmpty() || dosis.isEmpty() || frecuencia.isEmpty() || instrucciones.isEmpty()) {
                Toast.makeText(context, "Por favor llena todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí necesitarás el ID del usuario actual.
                // Podrías pasarlo como argumento a través del Safe Args,
                // o recuperarlo de SharedPreferences si lo guardaste en el login.
                // Por ahora, usaremos un ID de usuario fijo para el ejemplo (ej. 1).
                val idUsuario = 1 // Reemplaza con el ID de usuario real

                val nuevoMedicamento = Medicamento(
                    nombre = nombre,
                    dosis = dosis,
                    frecuencia = frecuencia,
                    instrucciones = instrucciones,
                    id_usuario = idUsuario
                )
                viewModel.agregarMedicamento(nuevoMedicamento)
                Toast.makeText(context, "Medicamento guardado.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Regresar al fragment anterior
            }
        }
    }
}