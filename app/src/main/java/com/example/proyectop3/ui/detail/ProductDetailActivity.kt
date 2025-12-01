package com.example.proyectop3.ui.detail

import android.os.Bundle
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

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productoActual: Producto
    private var precioFinal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // 1. Recibir el objeto Producto enviado desde el Menú
        // (El '?' es por si llega nulo, evitar que truene la app)
        productoActual = intent.getSerializableExtra("producto") as? Producto ?: return

        // 2. Vincular vistas
        val imgProducto = findViewById<ImageView>(R.id.imgDetalleProducto)
        val tvNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val tvPrecio = findViewById<TextView>(R.id.tvDetallePrecio)
        val spinnerLeche = findViewById<Spinner>(R.id.spinnerLeche)
        val spinnerTemp = findViewById<Spinner>(R.id.spinnerTemperatura)
        val switchVaso = findViewById<Switch>(R.id.switchVaso)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarCarrito)

        // 3. Mostrar datos del producto
        tvNombre.text = productoActual.nombre
        tvPrecio.text = "$${productoActual.precioBase}"
        precioFinal = productoActual.precioBase
        // imgProducto.setImageResource(productoActual.imagenResId) // Descomenta si usas iconos reales

        // 4. Configurar Spinners (Listas desplegables)
        val opcionesLeche = arrayOf("Leche Entera", "Deslactosada", "Almendra (+ $10)", "Avena (+ $10)")
        val opcionesTemp = arrayOf("Muy Caliente", "Caliente", "Tibio", "Frío")

        val adapterLeche = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesLeche)
        val adapterTemp = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTemp)

        spinnerLeche.adapter = adapterLeche
        spinnerTemp.adapter = adapterTemp

        // 5. Lógica del Switch "Traje mi Vaso" (Descuento)
        switchVaso.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                precioFinal = productoActual.precioBase - 5.0
                Toast.makeText(this, "¡Descuento ecológico aplicado!", Toast.LENGTH_SHORT).show()
            } else {
                precioFinal = productoActual.precioBase
            }
            tvPrecio.text = "$$precioFinal"
        }

        // 6. Botón Agregar al Pedido
        btnAgregar.setOnClickListener {
            val lecheSeleccionada = spinnerLeche.selectedItem.toString()
            val tempSeleccionada = spinnerTemp.selectedItem.toString()

            // Aquí guardarías el pedido en la BD o lista global
            // Por ahora, solo mostramos confirmación
            Toast.makeText(
                this,
                "Agregado: ${productoActual.nombre} ($lecheSeleccionada, $tempSeleccionada)",
                Toast.LENGTH_LONG
            ).show()

            // Cierra la actividad y vuelve al menú
            finish()
        }
    }
}