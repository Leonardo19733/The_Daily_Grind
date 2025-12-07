package com.example.proyectop3.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectop3.R
import com.example.proyectop3.model.Producto
import com.example.proyectop3.storage.LocalDatabase

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productoActual: Producto
    private var precioFinal: Double = 0.0

    // Vistas que necesitamos acceder globalmente
    private lateinit var tvPrecio: TextView
    private lateinit var spinnerLeche: Spinner
    private lateinit var switchVaso: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productoActual = intent.getSerializableExtra("producto") as? Producto ?: return

        val imgProducto = findViewById<ImageView>(R.id.imgDetalleProducto)
        val tvNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        tvPrecio = findViewById<TextView>(R.id.tvDetallePrecio) // Inicializamos variable global
        spinnerLeche = findViewById<Spinner>(R.id.spinnerLeche)
        val spinnerTemp = findViewById<Spinner>(R.id.spinnerTemperatura)
        switchVaso = findViewById<Switch>(R.id.switchVaso)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarCarrito)

        // Set inicial
        tvNombre.text = productoActual.nombre
        imgProducto.setImageResource(productoActual.imagenDetalle)
        precioFinal = productoActual.precioBase
        actualizarTextoPrecio()

        // Configurar Spinners
        val opcionesLeche = arrayOf("Leche Entera", "Deslactosada", "Almendra (+ $10)", "Avena (+ $10)")
        val opcionesTemp = arrayOf("Muy Caliente", "Caliente", "Tibio", "Frío")

        val adapterLeche = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesLeche)
        val adapterTemp = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTemp)

        spinnerLeche.adapter = adapterLeche
        spinnerTemp.adapter = adapterTemp

        // --- LÓGICA DE CAMBIO DE PRECIO EN TIEMPO REAL ---

        // 1. Escuchar cambios en la Leche
        spinnerLeche.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calcularNuevoPrecio()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 2. Escuchar cambios en el Switch de Vaso
        switchVaso.setOnCheckedChangeListener { _, _ ->
            calcularNuevoPrecio()
        }

        // --- BOTÓN AGREGAR ---
        btnAgregar.setOnClickListener {
            val lecheSeleccionada = spinnerLeche.selectedItem.toString()
            val tempSeleccionada = spinnerTemp.selectedItem.toString()
            val traeVaso = switchVaso.isChecked

            // Construimos una descripción detallada
            // Agregamos una marca clave "[Vaso Propio]" para detectarlo después en el carrito
            val marcaVaso = if (traeVaso) " | Vaso Propio" else ""
            val descripcionFinal = "$lecheSeleccionada, $tempSeleccionada$marcaVaso"

            val productoParaCarrito = productoActual.copy(
                precioBase = precioFinal,
                descripcion = descripcionFinal
            )

            LocalDatabase.agregarAlCarrito(productoParaCarrito)
            Toast.makeText(this, "Agregado: $${precioFinal}", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun calcularNuevoPrecio() {
        var costoExtra = 0.0
        var descuento = 0.0

        // Regla: Leche Almendra (pos 2) y Avena (pos 3) cuestan $10 extra
        val posicionLeche = spinnerLeche.selectedItemPosition
        if (posicionLeche == 2 || posicionLeche == 3) {
            costoExtra = 10.0
        }

        // Regla: Si trae vaso, descuenta $5
        if (switchVaso.isChecked) {
            descuento = 5.0
        }

        // Cálculo final
        precioFinal = productoActual.precioBase + costoExtra - descuento
        actualizarTextoPrecio()
    }

    private fun actualizarTextoPrecio() {
        tvPrecio.text = "$${String.format("%.2f", precioFinal)}"
    }
}