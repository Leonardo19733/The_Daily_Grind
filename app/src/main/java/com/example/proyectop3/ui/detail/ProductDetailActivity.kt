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

        productoActual = intent.getSerializableExtra("producto") as? Producto ?: return

        val imgProducto = findViewById<ImageView>(R.id.imgDetalleProducto)
        val tvNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val tvPrecio = findViewById<TextView>(R.id.tvDetallePrecio)
        val spinnerLeche = findViewById<Spinner>(R.id.spinnerLeche)
        // Se usa la variable spinnerTemp para referirse al ID spinnerTemperatura
        val spinnerTemp = findViewById<Spinner>(R.id.spinnerTemperatura)
        val switchVaso = findViewById<Switch>(R.id.switchVaso)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarCarrito)

        tvNombre.text = productoActual.nombre
        tvPrecio.text = "$${productoActual.precioBase}"
        precioFinal = productoActual.precioBase
        imgProducto.setImageResource(productoActual.imagenDetalle)

        val opcionesLeche = arrayOf("Leche Entera", "Deslactosada", "Almendra (+ $10)", "Avena (+ $10)")
        val opcionesTemp = arrayOf("Muy Caliente", "Caliente", "Tibio", "Frío")

        val adapterLeche = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesLeche)
        val adapterTemp = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTemp)

        spinnerLeche.adapter = adapterLeche
        // Se usa la variable correcta
        spinnerTemp.adapter = adapterTemp

        switchVaso.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                precioFinal = productoActual.precioBase - 5.0
                Toast.makeText(this, "¡Descuento ecológico aplicado!", Toast.LENGTH_SHORT).show()
            } else {
                precioFinal = productoActual.precioBase
            }
            tvPrecio.text = "$$precioFinal"
        }

        btnAgregar.setOnClickListener {
            val lecheSeleccionada = spinnerLeche.selectedItem.toString()
            // Se usa la variable correcta
            val tempSeleccionada = spinnerTemp.selectedItem.toString()

            Toast.makeText(
                this,
                "Agregado: ${productoActual.nombre} ($lecheSeleccionada, $tempSeleccionada)",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }
}