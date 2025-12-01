package com.example.proyectop3.storage
import com.example.thedailygrind.model.ClienteEstandar
import com.example.thedailygrind.model.Producto
import com.example.thedailygrind.model.Usuario

object LocalDatabase {

    // 1. Lista simulada de Usuarios (Cumple Req. 4: Estructura de datos)
    private val usuarios: MutableList<Usuario> = mutableListOf(
        ClienteEstandar(
            idUsuario = 1,
            nombreCompleto = "Angel Vaca",
            correoElectronico = "angel@daily.com",
            contrasena = "12345",
            telefono = "5551234567",
            puntosLealtad = 120,
            nivelLealtad = "Plata",
            vasosSalvados = 5
        ),
        ClienteEstandar(
            idUsuario = 2,
            nombreCompleto = "Devin Vazquez",
            correoElectronico = "devin@daily.com",
            contrasena = "admin",
            telefono = "5559876543"
        )
    )

    // 2. Lista simulada de Productos (Para el Menú)
    private val menuProductos: List<Producto> = listOf(
        Producto(1, "Latte Clásico", "Espresso con leche texturizada", 55.0, "Café", 0),
        Producto(2, "Cappuccino", "Espresso con espuma densa", 60.0, "Café", 0),
        Producto(3, "Cold Brew", "Infusión en frío por 12 horas", 65.0, "Frío", 0),
        Producto(4, "Matcha Latte", "Té verde japonés con leche", 70.0, "Té", 0)
    )

    // --- MÉTODOS SIMULADOS DE "BACKEND" ---

    // Función para validar Login (Req. 1: Acceso controlado)
    fun validarCredenciales(correo: String, pass: String): Usuario? {
        return usuarios.find { it.correoElectronico == correo && it.contrasena == pass }
    }

    // Función para obtener menú
    fun obtenerMenu(): List<Producto> {
        return menuProductos
    }

    // Función para registrar nuevo usuario
    fun registrarUsuario(nuevoUsuario: ClienteEstandar) {
        usuarios.add(nuevoUsuario)
    }
}