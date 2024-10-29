package com.example.nascar_app.data.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class LoginPreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("login_preferences", Context.MODE_PRIVATE)

    fun saveLoginDetails(email: String, password: String) {
        with(sharedPreferences.edit()) {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    fun getLoginDetails(): Pair<String?, String?> {
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        return Pair(email, password)
    }

    fun clearLoginDetails() {
        with(sharedPreferences.edit()) {
            remove("email")
            remove("password")
            apply()
        }
    }
}