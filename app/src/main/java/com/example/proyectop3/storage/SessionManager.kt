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
        editor.clear()
        editor.apply()
    }

    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "Usuario Invitado") ?: "Usuario Invitado"
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

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
}