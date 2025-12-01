package com.example.proyectop3.storage

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    // Definimos el archivo "TheDailyGrindPrefs"
    private val prefs: SharedPreferences = context.getSharedPreferences("TheDailyGrindPrefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_ID = "userId"
        const val KEY_USER_NAME = "userName"
    }

    // Guardar sesión al hacer Login exitoso
    fun createLoginSession(userId: Int, userName: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, userId)
        editor.putString(KEY_USER_NAME, userName)
        editor.apply() // Guardar cambios
    }

    // Verificar si ya está logueado
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Borrar sesión (Logout)
    fun logoutUser() {
        editor.clear()
        editor.apply()
    }

    // Obtener ID del usuario actual
    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    // Obtener Nombre del usuario actual (Vital para el Perfil)
    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "Usuario Invitado") ?: "Usuario Invitado"
    }
}