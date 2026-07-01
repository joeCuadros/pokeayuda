package com.unsa.pokeayuda.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.unsa.pokeayuda.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class AppPreferencesImpl(
    private val context: Context
) : AppPreferences {
    companion object {
        private val GENERATION = stringPreferencesKey("generation")
        private val THEME = stringPreferencesKey("theme")
        private val SYNC_DAYS = intPreferencesKey("sync_days")
    }
    override val generation: Flow<String> =
        context.dataStore.data.map {
            it[GENERATION] ?: "generation-i"
        }
    override val theme: Flow<Theme> =
        context.dataStore.data.map {
            Theme.valueOf(it[THEME] ?: Theme.SYSTEM.name)
        }
    override val syncDays: Flow<Int> =
        context.dataStore.data.map {
            it[SYNC_DAYS] ?: 7
        }
    override suspend fun setGeneration(generation: String) {
        context.dataStore.edit {
            it[GENERATION] = generation
        }
    }
    override suspend fun setTheme(theme: Theme) {
        context.dataStore.edit {
            it[THEME] = theme.name
        }
    }
    override suspend fun setSyncDays(days: Int) {
        require(days > 0)
        context.dataStore.edit {
            it[SYNC_DAYS] = days
        }
    }
}