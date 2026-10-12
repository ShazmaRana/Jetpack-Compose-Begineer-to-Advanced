package com.example.viewmodelapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PasswordVM : ViewModel() {

    // ---------- STATE (the only things we actually store) ----------
    var password by mutableStateOf("")
        private set                       // UI can read, but cannot change

    var isPasswordVisible by mutableStateOf(false)
        private set

    // ---------- DERIVED VALUES (calculated, not stored) ----------
    val hasMinLength get() = password.length >= 8
    val hasUpper     get() = password.any { it.isUpperCase() }
    val hasLower     get() = password.any { it.isLowerCase() }
    val hasDigit     get() = password.any { it.isDigit() }
    val hasSymbol    get() = password.any { !it.isLetterOrDigit() }

    val strength: Float
        get() {
            if (password.isEmpty()) return 0f
            val passed = listOf(hasMinLength, hasUpper, hasLower, hasDigit, hasSymbol)
                .count { it }
            return passed / 5f            // 0.0, 0.2, 0.4 ... 1.0
        }

    val strengthLabel: String
        get() = when {
            password.isEmpty() -> ""
            strength >= 1f     -> "Very Strong"
            strength >= 0.8f   -> "Strong"
            strength >= 0.6f   -> "Medium"
            else               -> "Weak"
        }

    // ---------- EVENTS (what the UI can ask us to do) ----------
    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }
}