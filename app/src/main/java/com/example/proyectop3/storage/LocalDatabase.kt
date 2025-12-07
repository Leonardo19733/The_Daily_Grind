package com.example.proyectop3.storage

import com.example.proyectop3.R
import com.example.proyectop3.model.Administrador
import com.example.proyectop3.model.ClienteEstandar
import com.example.proyectop3.model.Producto
import com.example.proyectop3.model.Usuario

object LocalDatabase {

    // 1. Usuarios
    private val usuarios: MutableList<Usuario> = mutableListOf(
        ClienteEstandar(1, "Angel Vaca", "angel@daily.com", "12345", "5551234567"),
        ClienteEstandar(2, "Devin Vazquez", "devin@daily.com", "12345", "5559876543"),
        Administrador(99, "Gerente", "admin@daily.com", "admin123", "5550000000", "EMP-001", "Gerencia")
    )

    // 2. Menú (Con tus imágenes)
    private val menuProductos: List<Producto> = listOf(
        Producto(1, "Latte Clásico", "Espresso con leche texturizada.", 55.0, "Café", R.drawable.ic_latte, R.drawable.banner_latte),
        Producto(2, "Cappuccino", "Espresso con espuma densa.", 60.0, "Café", R.drawable.ic_capuchino, R.drawable.banner_capuchino),
        Producto(3, "Cold Brew", "Infusión en frío por 12 horas.", 65.0, "Frío", R.drawable.ic_coldbrew, R.drawable.banner_coldbrew),
        Producto(4, "Matcha Latte", "Té verde japonés ceremonial.", 70.0, "Té", R.drawable.ic_matcha, R.drawable.banner_matcha),
        Producto(5, "Flat White", "Doble espresso con microespuma.", 58.0, "Café", R.drawable.ic_flatwhite, R.drawable.banner_flatwhite),
        Producto(6, "Espresso Americano", "Espresso diluido en agua caliente.", 45.0, "Café", R.drawable.ic_americano, R.drawable.banner_americano)
    )

    // 3. CARRITO DE COMPRAS (NUEVO)
    private val carrito: MutableList<Producto> = mutableListOf()

    fun validarCredenciales(correo: String, pass: String): Usuario? {
        return usuarios.find { it.correoElectronico == correo && it.contrasena == pass }
    }

    fun registrarUsuario(nuevoUsuario: ClienteEstandar) {
        usuarios.add(nuevoUsuario)
    }

    fun obtenerMenu(): List<Producto> {
        return menuProductos
    }

    // --- NUEVAS FUNCIONES PARA EL CARRITO ---
    fun agregarAlCarrito(producto: Producto) {
        carrito.add(producto)
    }

    fun obtenerCarrito(): List<Producto> {
        return carrito
    }

    fun calcularTotalCarrito(): Double {
        return carrito.sumOf { it.precioBase }
    }

    fun limpiarCarrito() {
        carrito.clear()
    }
}