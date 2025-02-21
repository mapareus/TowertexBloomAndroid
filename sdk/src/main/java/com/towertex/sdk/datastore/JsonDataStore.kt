package com.towertex.sdk.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.io.IOException

class JsonDataStore(
    context: Context
): JsonDataStoreContract {

    private val dataStore: DataStore<Preferences> = context.dataStore

    companion object {
        private const val JSON_KEY = "json_key"
        private val JSON_PREFERENCE_KEY = stringPreferencesKey(JSON_KEY)
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = JSON_KEY)
    }

    override val jsonFlow: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e("JsonRepository", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .mapNotNull { it[JSON_PREFERENCE_KEY] }

    override suspend fun store(formJson: String) {
        dataStore.edit { preferences ->
            preferences[JSON_PREFERENCE_KEY] = formJson
        }
    }
}