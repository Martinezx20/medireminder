package com.example.medireminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medireminder.R
import com.example.medireminder.database.Medicamento

class MedicamentoAdapter(
    private val medicamentos: List<Medicamento>,
    private val onItemClick: (Medicamento) -> Unit // Añadir un listener
) : RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder>() {

    class MedicamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreMedicamento)
        val dosis: TextView = itemView.findViewById(R.id.tvDosisMedicamento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicamento, parent, false)
        return MedicamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        val medicamento = medicamentos[position]
        holder.nombre.text = medicamento.nombre
        holder.dosis.text = medicamento.dosis

        // Configurar el click listener para el elemento
        holder.itemView.setOnClickListener {
            onItemClick(medicamento)
        }
    }

    override fun getItemCount() = medicamentos.size
}