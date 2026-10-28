package com.example.intencionesimplicitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LugarAdapter(
    private val lugares: List<Lugar>,
    private val onItemClick: (Lugar) -> Unit,
) : RecyclerView.Adapter<LugarAdapter.LugarViewHolder>() {

    class LugarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLugar: ImageView = itemView.findViewById(R.id.imgLugar)
        val tvNombreLugar: TextView = itemView.findViewById(R.id.tvNombreLugar)
        val tvDescripcionLugar: TextView = itemView.findViewById(R.id.tvDescripcionLugar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lugar, parent, false)
        return LugarViewHolder(view)
    }

    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        val lugar = lugares[position]
        holder.tvNombreLugar.text = lugar.nombre
        holder.tvDescripcionLugar.text = lugar.descripcion
        holder.imgLugar.setImageResource(lugar.imagen)

        holder.itemView.setOnClickListener {
            onItemClick(lugar)
        }
    }

    override fun getItemCount(): Int = lugares.size
}
