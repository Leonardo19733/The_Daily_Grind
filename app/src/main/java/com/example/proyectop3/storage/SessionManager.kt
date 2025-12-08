package com.example.proyectop3.storage

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("TheDailyGrindPrefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_ID = "userId"
        const val KEY_USER_NAME = "userName"

        const val KEY_FLOW_START_TIME = "flowStartTime"
        const val KEY_FLOW_IS_ACTIVE = "flowIsActive"
    }

    // --- LOGIN ---
    fun createLoginSession(userId: Int, userName: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, userId)
        editor.putString(KEY_USER_NAME, userName)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logoutUser() {
        // CORRECCIÓN: No usamos clear(). Solo removemos los datos de sesión activa.
        // Así los datos históricos (como vasos) se quedan guardados en el teléfono.
        editor.remove(KEY_IS_LOGGED_IN)
        editor.remove(KEY_USER_ID)
        editor.remove(KEY_USER_NAME)
        // También reseteamos el Flow Zone por seguridad
        editor.remove(KEY_FLOW_IS_ACTIVE)
        editor.remove(KEY_FLOW_START_TIME)
        editor.apply()
    }

    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "Usuario Invitado") ?: "Usuario Invitado"
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    // --- FLOW ZONE ---
    fun startFlowSession() {
        editor.putBoolean(KEY_FLOW_IS_ACTIVE, true)
        editor.putLong(KEY_FLOW_START_TIME, System.currentTimeMillis())
        editor.apply()
    }

    fun stopFlowSession() {
        editor.putBoolean(KEY_FLOW_IS_ACTIVE, false)
        editor.putLong(KEY_FLOW_START_TIME, 0)
        editor.apply()
    }

    fun isFlowActive(): Boolean {
        return prefs.getBoolean(KEY_FLOW_IS_ACTIVE, false)
    }

    fun getFlowStartTime(): Long {
        return prefs.getLong(KEY_FLOW_START_TIME, 0)
    }

    // --- VASOS SALVADOS (POR USUARIO) ---

    // Generamos una clave única para cada usuario: "saved_cups_1", "saved_cups_99", etc.
    private fun getCupsKey(): String {
        val userId = getUserId()
        return "saved_cups_$userId"
    }

    fun getSavedCups(): Int {
        if (!isLoggedIn()) return 0
        return prefs.getInt(getCupsKey(), 0)
    }

    fun incrementSavedCups(cantidad: Int) {
        if (!isLoggedIn()) return
        val key = getCupsKey()
        val actuales = prefs.getInt(key, 0)
        editor.putInt(key, actuales + cantidad)
        editor.apply()
    }
}