package com.unsa.pokeayuda.data.repository

import android.util.Log
import com.unsa.pokeayuda.data.local.datastore.AppPreferences
import com.unsa.pokeayuda.domain.model.Theme
import com.unsa.pokeayuda.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : AppPreferencesRepository {

    override val generation: Flow<String> = appPreferences.generation
        .catch { e ->
            Log.d("DEBUG", "Error leyendo DataStore generation: ${e.message}")
        }

    override val theme: Flow<Theme> = appPreferences.theme
        .catch { e ->
            Log.d("DEBUG", "Error leyendo DataStore theme: ${e.message}")
        }

    override val syncDays: Flow<Int> = appPreferences.syncDays
        .catch { e ->
            Log.d("DEBUG", "Error leyendo DataStore syncDays: ${e.message}")
        }

    override suspend fun setGeneration(generation: String) {
        try {
            appPreferences.setGeneration(generation)
        } catch (e: Exception) {
            Log.d("DEBUG", "Error escribiendo DataStore setGeneration: ${e.message}")
        }
    }

    override suspend fun setTheme(theme: Theme) {
        try {
            appPreferences.setTheme(theme)
        } catch (e: Exception) {
            Log.d("DEBUG", "Error escribiendo DataStore setTheme: ${e.message}")
        }
    }

    override suspend fun setSyncDays(days: Int) {
        try {
            appPreferences.setSyncDays(days)
        } catch (e: Exception) {
            Log.d("DEBUG", "Error escribiendo DataStore setSyncDays: ${e.message}")
        }
    }
}