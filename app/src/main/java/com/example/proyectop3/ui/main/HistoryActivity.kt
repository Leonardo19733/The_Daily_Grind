package com.example.proyectop3.ui.main

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectop3.R
import com.example.proyectop3.storage.LocalDatabase
import com.example.proyectop3.storage.SessionManager

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val listView = findViewById<ListView>(R.id.listViewHistory)
        val sessionManager = SessionManager(this)

        // Obtener historial del usuario actual
        val historial = LocalDatabase.obtenerHistorialPorUsuario(sessionManager.getUserId())

        // Crear una lista de Strings simple para mostrar
        val listaVisual = historial.map {
            "📅 ${it.fecha}\n☕ ${it.descripcion}\n💰 Total: $${it.total}"
        }

        if (listaVisual.isEmpty()) {
            val emptyList = listOf("No tienes pedidos pasados aún.")
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emptyList)
        } else {
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaVisual)
        }
    }
}