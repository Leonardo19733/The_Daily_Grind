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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerMenu)
        val fabCarrito = view.findViewById<FloatingActionButton>(R.id.fabCarrito)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val productos = LocalDatabase.obtenerMenu()

        val adapter = ProductAdapter(productos) { productoSeleccionado ->
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("producto", productoSeleccionado)
            startActivity(intent)
        }

        val cardBanner = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardBanner)

        cardBanner.setOnClickListener {
            val intent = Intent(context, ShopInfoActivity::class.java)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        // ACCIÓN DEL BOTÓN FLOTANTE
        fabCarrito.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}