package com.example.medireminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medireminder.R
import com.example.medireminder.database.Sintoma

class SintomaAdapter(private val sintomas: List<Sintoma>) : RecyclerView.Adapter<SintomaAdapter.SintomaViewHolder>() {
    class SintomaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        holder.intensidad.text = sintoma.intensidad
    }

    override fun getItemCount() = sintomas.size
}