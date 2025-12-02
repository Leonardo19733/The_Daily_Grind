package com.example.proyectop3.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectop3.R // <--- IMPORTANTE: Esto arregla las referencias rojas
import com.example.proyectop3.model.ClienteEstandar
import com.example.proyectop3.storage.LocalDatabase
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Vincular las vistas con el XML
        // Si estos IDs salen en rojo, asegúrate de haber copiado el XML de abajo
        val etNombre = findViewById<EditText>(R.id.etNombreReg)
        val etCorreo = findViewById<EditText>(R.id.etCorreoReg)
        val etPass = findViewById<EditText>(R.id.etPassReg)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoReg)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val tel = etTelefono.text.toString().trim()

            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Faltan datos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                // CORRECCIÓN AQUÍ: Quitamos 'fechaRegistro' porque se genera sola
                val nuevoCliente = ClienteEstandar(
                    idUsuario = Random.nextInt(100, 9999),
                    nombreCompleto = nombre,
                    correoElectronico = correo,
                    contrasena = pass,
                    telefono = tel
                    // Eliminada la línea: fechaRegistro = Date()
                )

                // Guardar en la base de datos falsa
                LocalDatabase.registrarUsuario(nuevoCliente)

                Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_LONG).show()

                // Cerrar esta pantalla para volver al Login
                finish()
            }
        }
    }
}