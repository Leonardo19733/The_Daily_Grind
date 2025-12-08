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
import com.example.proyectop3.model.Orden
import com.example.proyectop3.storage.LocalDatabase
import com.example.proyectop3.storage.SessionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class CartActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // 1. Inicializar SessionManager
        sessionManager = SessionManager(this)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCarrito)
        val tvTotal = findViewById<TextView>(R.id.tvTotalPagar)
        val btnPagar = findViewById<Button>(R.id.btnPagar)

        // 2. Obtener datos del carrito actual
        val carrito = LocalDatabase.obtenerCarrito()
        val total = LocalDatabase.calcularTotalCarrito()

        // 3. Mostrar Total
        tvTotal.text = "$${String.format("%.2f", total)}"

        // 4. Configurar la lista visual
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ProductAdapter(carrito) {
            // Acción al hacer click en un item (opcional)
            Toast.makeText(this, it.nombre, Toast.LENGTH_SHORT).show()
        }

        // 5. Lógica del Botón PAGAR
        btnPagar.setOnClickListener {
            if (carrito.isNotEmpty()) {

                // --- A) LÓGICA DE VASOS SALVADOS ---
                // Contamos cuántos productos dicen "Vaso Propio" en su descripción
                val vasosAhorrados = carrito.count { it.descripcion.contains("Vaso Propio") }

                // Si ahorró vasos, los sumamos al SessionManager permanentemente
                if (vasosAhorrados > 0) {
                    sessionManager.incrementSavedCups(vasosAhorrados)
                }

                // --- B) LÓGICA DE HISTORIAL (NUEVO) ---
                val userId = sessionManager.getUserId()

                // Creamos un resumen simple tipo string: "Latte, Matcha, Pan"
                val itemsResumen = carrito.joinToString(", ") { it.nombre }

                // Obtenemos la fecha de hoy
                val fechaHoy = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

                // Creamos el objeto Orden
                val nuevaOrden = Orden(
                    id = Random.nextInt(10000, 99999), // ID al azar
                    userId = userId,
                    fecha = fechaHoy,
                    descripcion = itemsResumen,
                    total = total
                )

                // Guardamos en la base de datos
                LocalDatabase.registrarOrdenHistorial(nuevaOrden)

                // --- C) LIMPIEZA Y SALIDA ---
                LocalDatabase.limpiarCarrito()

                val mensaje = if (vasosAhorrados > 0)
                    "¡Pago Exitoso! Ahorraste $vasosAhorrados vaso(s) 🌱"
                else
                    "¡Pago Exitoso! Tu pedido está en preparación."

                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

                finish() // Cierra la pantalla y vuelve al menú

            } else {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}