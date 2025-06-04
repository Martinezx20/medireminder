package com.example.medireminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView // Importa RecyclerView
import com.example.medireminder.R
import com.example.medireminder.database.Sintoma

// Línea 11
class SintomaAdapter(private val sintomas: List<Sintoma>) : RecyclerView.Adapter<SintomaAdapter.SintomaViewHolder>() {
    // Línea 12 (aproximada)
    class SintomaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // <-- ¡CORRECCIÓN AQUÍ!
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcionSintoma)
        val intensidad: TextView = itemView.findViewById(R.id.tvIntensidadSintoma)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SintomaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sintoma, parent, false)
        return SintomaViewHolder(view)
    }

    override fun onBindViewHolder(holder: SintomaViewHolder, position: Int) {
        val sintoma = sintomas[position]
        holder.descripcion.text = sintoma.descripcion
        holder.intensidad.text = sintoma.intensidad.toString() // La corrección anterior
    }

    override fun getItemCount() = sintomas.size
}