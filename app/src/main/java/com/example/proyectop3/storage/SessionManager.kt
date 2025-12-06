package com.example.proyectop3.storage

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    // Nombre del archivo donde se guardan los datos en el celular
    private val prefs: SharedPreferences = context.getSharedPreferences("TheDailyGrindPrefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    companion object {
        // Claves para el Login
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_ID = "userId"
        const val KEY_USER_NAME = "userName"

        // Claves para el Flow Zone (Cronómetro)
        const val KEY_FLOW_START_TIME = "flowStartTime"
        const val KEY_FLOW_IS_ACTIVE = "flowIsActive"
    }

    // --- SECCIÓN LOGIN ---

    fun createLoginSession(userId: Int, userName: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, userId)
        editor.putString(KEY_USER_NAME, userName)
        editor.apply() // Guardar cambios
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logoutUser() {
        // Al cerrar sesión, borramos todo (incluyendo el cronómetro si estaba activo)
        editor.clear()
        editor.apply()
    }

    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "Usuario Invitado") ?: "Usuario Invitado"
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    // --- SECCIÓN FLOW ZONE (CRONÓMETRO PERSISTENTE) ---

    // 1. Iniciar sesión de trabajo (Guardamos la hora exacta del sistema)
    fun startFlowSession() {
        editor.putBoolean(KEY_FLOW_IS_ACTIVE, true)
        editor.putLong(KEY_FLOW_START_TIME, System.currentTimeMillis())
        editor.apply()
    }

    // 2. Detener sesión de trabajo
    fun stopFlowSession() {
        editor.putBoolean(KEY_FLOW_IS_ACTIVE, false)
        editor.putLong(KEY_FLOW_START_TIME, 0)
        editor.apply()
    }

    // 3. Consultar si el cronómetro debe estar corriendo
    fun isFlowActive(): Boolean {
        return prefs.getBoolean(KEY_FLOW_IS_ACTIVE, false)
    }

    // 4. Recuperar a qué hora empezó para calcular la diferencia
    fun getFlowStartTime(): Long {
        return prefs.getLong(KEY_FLOW_START_TIME, 0)
    }
}