package com.example.medireminder.fragments

import android.app.TimePickerDialog
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
import com.example.medireminder.database.Recordatorio
import com.example.medireminder.viewmodel.MedicamentoViewModel
import com.example.medireminder.viewmodel.RecordatorioViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddRecordatorioFragment : Fragment(R.layout.fragment_add_recordatorio) {

    private lateinit var recordatorioViewModel: RecordatorioViewModel
    private lateinit var medicamentoViewModel: MedicamentoViewModel
    private lateinit var etHoraRecordatorio: EditText
    private lateinit var spinnerMedicamentos: Spinner
    private var selectedMedicamento: Medicamento? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordatorioViewModel = ViewModelProvider(this).get(RecordatorioViewModel::class.java)
        medicamentoViewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        etHoraRecordatorio = view.findViewById(R.id.etHoraRecordatorio)
        spinnerMedicamentos = view.findViewById(R.id.spinnerMedicamentos)
        val etTipoAlerta = view.findViewById<EditText>(R.id.etTipoAlerta)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarRecordatorio)

        // Cargar medicamentos para el Spinner
        // Asumiendo que tenemos el ID del usuario actual.
        // Similar a MedicamentosFragment, reemplaza con el ID de usuario real.
        val idUsuario = 1
        medicamentoViewModel.cargarMedicamentos(idUsuario)
        medicamentoViewModel.medicamentos.observe(viewLifecycleOwner) { medicamentos ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, medicamentos.map { it.nombre })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMedicamentos.adapter = adapter
            if (medicamentos.isNotEmpty()) {
                selectedMedicamento = medicamentos[0] // Selecciona el primero por defecto
            }
        }

        // Selector de hora
        etHoraRecordatorio.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(requireContext(), { _, h, m ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, h)
                selectedTime.set(Calendar.MINUTE, m)
                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                etHoraRecordatorio.setText(sdf.format(selectedTime.time))
            }, hour, minute, true) // true para formato 24 horas
            timePickerDialog.show()
        }

        btnGuardar.setOnClickListener {
            val hora = etHoraRecordatorio.text.toString().trim()
            val tipoAlerta = etTipoAlerta.text.toString().trim()
            val selectedMedicamentoId = selectedMedicamento?.id_medicamento

            if (hora.isEmpty() || tipoAlerta.isEmpty() || selectedMedicamentoId == null) {
                Toast.makeText(context, "Por favor selecciona un medicamento y llena todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoRecordatorio = Recordatorio(
                    hora = hora,
                    tipo_alerta = tipoAlerta,
                    id_medicamento = selectedMedicamentoId
                )
                recordatorioViewModel.agregarRecordatorio(nuevoRecordatorio)
                Toast.makeText(context, "Recordatorio guardado.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack() // Regresar al fragment anterior
            }
        }
    }
}