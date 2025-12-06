package com.example.proyectop3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectop3.R
import com.example.proyectop3.model.Producto

class ProductAdapter(
    private val listaProductos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit // Función que se ejecuta al dar click
) : RecyclerView.Adapter<ProductAdapter.ProductoViewHolder>() {

    // Clase interna para "sostener" las vistas de cada tarjeta
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgProducto)
        val nombre: TextView = view.findViewById(R.id.txtNombreProducto)
        val precio: TextView = view.findViewById(R.id.txtPrecioProducto)
        val btn: Button = view.findViewById(R.id.btnAgregar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        // Asignamos los datos a la vista
        holder.nombre.text = producto.nombre
        holder.precio.text = "$${producto.precioBase}0"

        holder.img.setImageResource(producto.imagenIcono)

        // Configurar el click en el botón "Ver" y en toda la tarjeta
        holder.itemView.setOnClickListener { onProductoClick(producto) }
        holder.btn.setOnClickListener { onProductoClick(producto) }


    }

    override fun getItemCount() = listaProductos.size
}