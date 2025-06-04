package com.example.medireminder.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medireminder.R
import com.example.medireminder.database.Medicamento
import com.example.medireminder.database.Sintoma // Asegúrate de que este es el Sintoma correcto
import com.example.medireminder.viewmodel.MedicamentoViewModel
import com.example.medireminder.viewmodel.SintomaViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddSintomaFragment : Fragment(R.layout.fragment_add_sintoma) {

    private lateinit var sintomaViewModel: SintomaViewModel
    private lateinit var medicamentoViewModel: MedicamentoViewModel
    private lateinit var etFechaSintoma: EditText
    private lateinit var spinnerDosisMedicamento: Spinner
    // Usaremos un nombre más claro, ya que en Sintoma.kt es id_medicamento
    private var selectedMedicamentoId: Int? = null // Cambiado de selectedMedicamentoDosisId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sintomaViewModel = ViewModelProvider(this).get(SintomaViewModel::class.java)
        medicamentoViewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        etFechaSintoma = view.findViewById(R.id.etFechaSintoma)
        spinnerDosisMedicamento = view.findViewById(R.id.spinnerDosisMedicamento)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcionSintoma)
        val etIntensidad = view.findViewById<EditText>(R.id.etIntensidadSintoma) // Input de texto para intensidad
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarSintoma)

        // Cargar medicamentos para el Spinner, mostrando "Nombre (Dosis)"
        val idUsuario = 1 // TODO: Obtener el ID de usuario real de SharedPreferences
        medicamentoViewModel.cargarMedicamentos(idUsuario)
        medicamentoViewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
                medicamentos.map { "${it.nombre} (${it.dosis})" })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDosisMedicamento.adapter = adapter

            // Si hay medicamentos, selecciona el primero por defecto
            if (medicamentos.isNotEmpty()) {
                selectedMedicamentoId = medicamentos[0].id_medicamento
            }
            // Puedes añadir un listener para actualizar selectedMedicamentoId cuando el usuario cambia la selección
            // spinnerDosisMedicamento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //     override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            //         selectedMedicamentoId = medicamentos[position].id_medicamento
            //     }
            //     override fun onNothingSelected(parent: AdapterView<*>?) {}
            // }
        }

        // Selector de fecha
        etFechaSintoma.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, y, m, d ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(y, m, d)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                etFechaSintoma.setText(sdf.format(selectedDate.time))
            }, year, month, day)
            datePickerDialog.show()
        }


        btnGuardar.setOnClickListener {
            val descripcion = etDescripcion.text.toString().trim()
            val intensidadString = etIntensidad.text.toString().trim() // Valor como String
            val fecha = etFechaSintoma.text.toString().trim()
            val selectedMedicamento = selectedMedicamentoId // Ahora es el ID del medicamento

            // Validaciones
            if (descripcion.isEmpty() || intensidadString.isEmpty() || fecha.isEmpty() || selectedMedicamento == null) {
                Toast.makeText(context, "Por favor llena todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Salir del listener si hay campos vacíos
            }

            // Convertir intensidad de String a Int de forma segura
            val intensidad: Int = intensidadString.toIntOrNull() ?: run {
                Toast.makeText(context, "Por favor ingresa un número válido para la intensidad.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Salir si la intensidad no es un número
            }

            val nuevoSintoma = Sintoma(
                descripcion = descripcion,
                intensidad = intensidad, // <-- ¡Ahora es un Int!
                fecha = fecha,
                id_medicamento = selectedMedicamento // <-- ¡Corregido el nombre del parámetro!
            )

            sintomaViewModel.agregarSintoma(nuevoSintoma)
            Toast.makeText(context, "Síntoma guardado.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack() // Regresar al fragment anterior
        }
    }
}