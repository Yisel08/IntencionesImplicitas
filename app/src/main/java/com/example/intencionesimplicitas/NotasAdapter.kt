package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotasAdapter(
    private val notas: MutableList<Nota>,
    private val onItemClick: (Nota, Int) -> Unit,
) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTituloNota: TextView = itemView.findViewById(R.id.tvTituloNota)
        val tvContenidoNota: TextView = itemView.findViewById(R.id.tvContenidoNota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.tvTituloNota.text = nota.titulo
        holder.tvContenidoNota.text = nota.contenido

        holder.itemView.setOnClickListener {
            onItemClick(nota, position)
        }
    }

    override fun getItemCount(): Int = notas.size

    fun agregarNota(nota: Nota) {
        notas.add(0, nota)
        notifyItemInserted(0)
    }

    fun actualizarNota(posicion: Int, notaActualizada: Nota) {
        if (posicion in 0 until notas.size) {
            notas[posicion] = notaActualizada
            notifyItemChanged(posicion)
        }
    }
}
