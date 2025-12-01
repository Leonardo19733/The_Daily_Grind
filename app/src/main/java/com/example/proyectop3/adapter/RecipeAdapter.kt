package com.example.proyectop3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectop3.R
import com.example.proyectop3.model.RecetaPerfecta

class RecipeAdapter(
    private val listaRecetas: List<RecetaPerfecta>,
    private val onRecetaClick: (RecetaPerfecta) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecetaViewHolder>() {

    class RecetaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.txtNombreProducto)
        val detalles: TextView = view.findViewById(R.id.txtPrecioProducto) // Usamos el campo precio para los detalles
        val btn: Button = view.findViewById(R.id.btnAgregar)
        val img: ImageView = view.findViewById(R.id.imgProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        // Reutilizamos el diseño item_producto para no crear otro XML
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = listaRecetas[position]

        holder.nombre.text = receta.nombrePersonalizado

        // Mostramos un resumen de la personalización (Ej: "Entera - Caliente")
        holder.detalles.text = "${receta.tipoLeche} • ${receta.temperatura}"
        holder.btn.text = "Pedir" // Cambiamos el texto del botón

        holder.btn.setOnClickListener { onRecetaClick(receta) }
    }

    override fun getItemCount() = listaRecetas.size
}