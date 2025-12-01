package com.example.proyectop3.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectop3.R
import com.example.proyectop3.adapter.ProductAdapter
import com.example.proyectop3.storage.LocalDatabase
import com.example.proyectop3.ui.detail.ProductDetailActivity

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerMenu)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Obtenemos los productos de nuestra base de datos falsa
        val productos = LocalDatabase.obtenerMenu()

        // Configuramos el adaptador con la acción de ir al Detalle
        val adapter = ProductAdapter(productos) { productoSeleccionado ->
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("producto", productoSeleccionado)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        return view
    }
}