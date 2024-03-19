package com.timeTonicApp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("TimetonicLibraryApp", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Guarda el token de sesión en SharedPreferences.
     * @param token Token de sesión que se obtiene luego de autenticarse con éxito.
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Recupera el token de sesión de SharedPreferences.
     * @return Token de sesión actualmente guardado.
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Elimina el token de sesión de SharedPreferences.
     */
    fun deleteAuthToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }
}
