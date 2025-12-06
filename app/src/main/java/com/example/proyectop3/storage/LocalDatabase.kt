package com.example.proyectop3.storage

import com.example.proyectop3.R
import com.example.proyectop3.model.Administrador
import com.example.proyectop3.model.ClienteEstandar
import com.example.proyectop3.model.Producto
import com.example.proyectop3.model.Usuario

object LocalDatabase {

    // ... (Tu lista de usuarios déjala igual que antes)
    private val usuarios: MutableList<Usuario> = mutableListOf(
        ClienteEstandar(1, "Angel Vaca", "angel@daily.com", "12345", "5551234567"),
        ClienteEstandar(2, "Devin Vazquez", "devin@daily.com", "12345", "5559876543"),
        Administrador(99, "Gerente", "admin@daily.com", "admin123", "5550000000", "EMP-001", "Gerencia")
    )

    // 2. Lista del Menú con TUS IMÁGENES EXACTAS
    private val menuProductos: List<Producto> = listOf(
        Producto(
            1,
            "Latte Clásico",
            "Espresso con leche texturizada y capa ligera de espuma.",
            55.0,
            "Café",
            R.drawable.ic_latte,       // Tu archivo ic_latte.jpg
            R.drawable.banner_latte    // Tu archivo banner_latte.jpg
        ),
        Producto(
            2,
            "Cappuccino",
            "Espresso con partes iguales de leche y espuma densa.",
            60.0,
            "Café",
            R.drawable.ic_capuchino,   // Ojo: Usé tu nombre 'capuchino'
            R.drawable.banner_capuchino
        ),
        Producto(
            3,
            "Cold Brew",
            "Infusión en frío por 12 horas, suave y cafeinado.",
            65.0,
            "Frío",
            R.drawable.ic_coldbrew,    // Ojo: Usé tu nombre 'coldbrew'
            R.drawable.banner_coldbrew
        ),
        Producto(
            4,
            "Matcha Latte",
            "Té verde japonés grado ceremonial con leche.",
            70.0,
            "Té",
            R.drawable.ic_matcha,
            R.drawable.banner_matcha
        ),
        Producto(
            5,
            "Flat White",
            "Doble espresso con capa fina de microespuma.",
            58.0,
            "Café",
            R.drawable.ic_flatwhite,   // Ojo: Usé tu nombre 'flatwhite'
            R.drawable.banner_flatwhite
        ),
        Producto(
            6,
            "Espresso Americano",
            "Espresso doble diluido en agua caliente.",
            45.0,
            "Café",
            R.drawable.ic_americano,
            R.drawable.banner_americano
        )
    )

    // ... (Tus funciones validarCredenciales, registrarUsuario, etc. déjalas igual)
    fun validarCredenciales(correo: String, pass: String): Usuario? {
        return usuarios.find { it.correoElectronico == correo && it.contrasena == pass }
    }

    fun obtenerMenu(): List<Producto> {
        return menuProductos
    }

    fun registrarUsuario(nuevoUsuario: ClienteEstandar) {
        usuarios.add(nuevoUsuario)
    }
}