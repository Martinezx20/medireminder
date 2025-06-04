package com.example.medireminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medireminder.R
import com.example.medireminder.database.Historial

class HistorialAdapter(private val historiales: List<Historial>) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {
    class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resumen: TextView = itemView.findViewById(R.id.tvResumenHistorial)
        val fecha: TextView = itemView.findViewById(R.id.tvFechaHistorial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val historial = historiales[position]
        holder.resumen.text = historial.resumen
        holder.fecha.text = "${historial.fecha_inicio} - ${historial.fecha_fin}"
    }

    override fun getItemCount() = historiales.size
}