package com.example.proyectop3.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectop3.R
import com.example.proyectop3.model.ClienteEstandar
import com.example.proyectop3.storage.LocalDatabase
import java.util.Date
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Requiere crear este layout

        val etNombre = findViewById<EditText>(R.id.etNombreReg)
        val etCorreo = findViewById<EditText>(R.id.etCorreoReg)
        val etPass = findViewById<EditText>(R.id.etPassReg)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoReg)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val pass = etPass.text.toString()
            val tel = etTelefono.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Faltan datos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                // Crear nuevo cliente
                val nuevoCliente = ClienteEstandar(
                    idUsuario = Random.nextInt(100, 9999), // ID aleatorio
                    nombreCompleto = nombre,
                    correoElectronico = correo,
                    contrasena = pass,
                    telefono = tel,
                    fechaRegistro = Date()
                )

                // Guardar en "Base de Datos"
                LocalDatabase.registrarUsuario(nuevoCliente)

                Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_LONG).show()

                // Volver al Login
                finish()
            }
        }
    }
}