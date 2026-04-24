package com.example.cinemaapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

object SessionKeys {
    val USER_NAME  = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
}

data class SessionData(
    val name:  String = "",
    val email: String = ""
)

class SessionManager(private val context: Context) {

    val sessionFlow: Flow<SessionData> = context.dataStore.data.map { prefs ->
        SessionData(
            name  = prefs[SessionKeys.USER_NAME]  ?: "",
            email = prefs[SessionKeys.USER_EMAIL] ?: ""
        )
    }

    suspend fun saveSession(name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[SessionKeys.USER_NAME]  = name
            prefs[SessionKeys.USER_EMAIL] = email
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(SessionKeys.USER_NAME)
            prefs.remove(SessionKeys.USER_EMAIL)
        }
    }
}