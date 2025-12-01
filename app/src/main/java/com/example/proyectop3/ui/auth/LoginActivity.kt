package com.example.proyectop3.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectop3.R
import com.example.proyectop3.storage.LocalDatabase
import com.example.proyectop3.storage.SessionManager
import com.example.proyectop3.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        // 1. Verificar si ya hay sesión (Auto-Login)
        if (sessionManager.isLoggedIn()) {
            irAMainActivity()
        }

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Si decidieras agregar un botón de "Registrarse" en el XML del login:
        // val tvRegistrar = findViewById<TextView>(R.id.tvIrARegistro)
        // tvRegistrar.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = LocalDatabase.validarCredenciales(correo, pass)

                if (usuario != null) {
                    sessionManager.createLoginSession(usuario.idUsuario, usuario.nombreCompleto)
                    Toast.makeText(this, "Bienvenido ${usuario.nombreCompleto}", Toast.LENGTH_SHORT).show()
                    irAMainActivity()
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_ERROR).show()
                }
            }
        }
    }

    private fun irAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}