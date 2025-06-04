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
import com.example.medireminder.database.Sintoma
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
    private var selectedMedicamentoDosisId: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sintomaViewModel = ViewModelProvider(this).get(SintomaViewModel::class.java)
        medicamentoViewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        etFechaSintoma = view.findViewById(R.id.etFechaSintoma)
        spinnerDosisMedicamento = view.findViewById(R.id.spinnerDosisMedicamento)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcionSintoma)
        val etIntensidad = view.findViewById<EditText>(R.id.etIntensidadSintoma)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarSintoma)

        // Cargar medicamentos para el Spinner, mostrando "Nombre (Dosis)"
        // Asumiendo que tenemos el ID del usuario actual.
        val idUsuario = 1
        medicamentoViewModel.cargarMedicamentos(idUsuario)
        medicamentoViewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
                medicamentos.map { "${it.nombre} (${it.dosis})" })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDosisMedicamento.adapter = adapter
            if (medicamentos.isNotEmpty()) {
                // Al seleccionar un item en el spinner, obtenemos el id_medicamento para id_dosis
                // En tu esquema actual, `Sintoma` tiene `id_dosis`. Si `id_dosis` se refiere a `id_medicamento` en este contexto, lo usaremos así.
                // Si `id_dosis` se refiere a una entidad `Dosis` separada, necesitarías una tabla Dosis y su DAO.
                selectedMedicamentoDosisId = medicamentos[0].id_medicamento
            }
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
            val intensidad = etIntensidad.text.toString().trim()
            val fecha = etFechaSintoma.text.toString().trim()
            val selectedDosisId = selectedMedicamentoDosisId

            if (descripcion.isEmpty() || intensidad.isEmpty() || fecha.isEmpty() || selectedDosisId == null) {
                Toast.makeText(context, "Por favor selecciona una dosis y llena todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoSintoma = Sintoma(
                    descripcion = descripcion,
                    intensidad = intensidad,
                    fecha = fecha,
                    id_dosis = selectedDosisId
                )
                sintomaViewModel.agregarSintoma(nuevoSintoma)
                Toast.makeText(context, "Síntoma guardado.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Regresar al fragment anterior
            }
        }
    }
}