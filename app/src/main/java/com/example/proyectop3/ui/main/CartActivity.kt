package com.example.proyectop3.ui.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectop3.R
import com.example.proyectop3.adapter.ProductAdapter
import com.example.proyectop3.storage.LocalDatabase
import com.example.proyectop3.storage.SessionManager // Importar

class CartActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager // Variable para usar SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Inicializar SessionManager
        sessionManager = SessionManager(this)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCarrito)
        val tvTotal = findViewById<TextView>(R.id.tvTotalPagar)
        val btnPagar = findViewById<Button>(R.id.btnPagar)

        val carrito = LocalDatabase.obtenerCarrito()
        val total = LocalDatabase.calcularTotalCarrito()

        tvTotal.text = "$${String.format("%.2f", total)}"

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ProductAdapter(carrito) {
            Toast.makeText(this, it.nombre, Toast.LENGTH_SHORT).show()
        }

        btnPagar.setOnClickListener {
            if (carrito.isNotEmpty()) {

                // --- LÓGICA DE VASOS SALVADOS ---
                // Contamos cuántos productos tienen la marca "Vaso Propio" en su descripción
                val vasosAhorradosEnEstePedido = carrito.count { it.descripcion.contains("Vaso Propio") }

                if (vasosAhorradosEnEstePedido > 0) {
                    sessionManager.incrementSavedCups(vasosAhorradosEnEstePedido)
                }

                LocalDatabase.limpiarCarrito()

                val mensaje = if (vasosAhorradosEnEstePedido > 0)
                    "¡Pago Exitoso! Ahorraste $vasosAhorradosEnEstePedido vasos hoy 🌱"
                else
                    "¡Pago Exitoso! Pedido en camino."

                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}