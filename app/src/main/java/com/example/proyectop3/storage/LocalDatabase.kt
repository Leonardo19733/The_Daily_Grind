package com.example.proyectop3.storage

import com.example.proyectop3.model.Administrador
import com.example.proyectop3.model.ClienteEstandar
import com.example.proyectop3.model.Producto
import com.example.proyectop3.model.Usuario

object LocalDatabase {

    // 1. Lista simulada de Usuarios (Inicialmente con datos de prueba)
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
            contrasena = "12345",
            telefono = "5559876543",
            puntosLealtad = 50,
            nivelLealtad = "Bronce",
            vasosSalvados = 2
        ),
        // Usuario Administrador (Requerimiento de roles)
        Administrador(
            idUsuario = 99,
            nombreCompleto = "Gerente General",
            correoElectronico = "admin@daily.com",
            contrasena = "admin123",
            telefono = "5550000000",
            idEmpleado = "EMP-001",
            areaAsignada = "Gerencia"
        )
    )

    // 2. Lista fija del Menú de Cafés
    private val menuProductos: List<Producto> = listOf(
        Producto(1, "Latte Clásico", "Espresso con leche texturizada y capa ligera de espuma.", 55.0, "Café", 0),
        Producto(2, "Cappuccino", "Espresso con partes iguales de leche y espuma densa.", 60.0, "Café", 0),
        Producto(3, "Cold Brew", "Infusión en frío por 12 horas, suave y cafeinado.", 65.0, "Frío", 0),
        Producto(4, "Matcha Latte", "Té verde japonés grado ceremonial con leche.", 70.0, "Té", 0),
        Producto(5, "Flat White", "Doble espresso con capa fina de microespuma.", 58.0, "Café", 0),
        Producto(6, "Espresso Americano", "Espresso doble diluido en agua caliente.", 45.0, "Café", 0)
    )

    // --- MÉTODOS SIMULADOS ---

    // Buscar usuario por correo y contraseña (Login)
    fun validarCredenciales(correo: String, pass: String): Usuario? {
        return usuarios.find { it.correoElectronico == correo && it.contrasena == pass }
    }

    // Obtener todo el menú
    fun obtenerMenu(): List<Producto> {
        return menuProductos
    }

    // Registrar un nuevo usuario (Desde RegisterActivity)
    fun registrarUsuario(nuevoUsuario: ClienteEstandar) {
        usuarios.add(nuevoUsuario)
    }
}