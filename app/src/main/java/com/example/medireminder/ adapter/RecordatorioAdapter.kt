package com.example.medireminder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medireminder.R
import com.example.medireminder.database.Recordatorio

class RecordatorioAdapter(private val recordatorios: List<Recordatorio>) : RecyclerView.Adapter<RecordatorioAdapter.RecordatorioViewHolder>() {
    class RecordatorioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hora: TextView = itemView.findViewById(R.id.tvHoraRecordatorio)
        val tipo: TextView = itemView.findViewById(R.id.tvTipoRecordatorio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordatorioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recordatorio, parent, false)
        return RecordatorioViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordatorioViewHolder, position: Int) {
        val recordatorio = recordatorios[position]
        holder.hora.text = recordatorio.hora
        holder.tipo.text = recordatorio.tipo_alerta
    }

    override fun getItemCount() = recordatorios.size
}