package com.unsa.pokeayuda.di

import android.content.Context
import androidx.room.Room
import com.unsa.pokeayuda.data.local.dao.AtaqueDao
import com.unsa.pokeayuda.data.local.dao.CadenaEvolutivaDao
import com.unsa.pokeayuda.data.local.dao.EquipoPokemonDao
import com.unsa.pokeayuda.data.local.dao.GeneracionPokemonDao
import com.unsa.pokeayuda.data.local.dao.HabilidadDao
import com.unsa.pokeayuda.data.local.dao.PokemonDao
import com.unsa.pokeayuda.data.local.dao.TipoDao
import com.unsa.pokeayuda.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pokeayuda.db"
        ).build()
    }

    @Provides
    fun provideEquipoPokemonDao(
        database: AppDatabase
    ): EquipoPokemonDao = database.equipoPokemonDao()

    @Provides
    fun providePokemonDao(
        database: AppDatabase
    ): PokemonDao = database.pokemonDao()

    @Provides
    fun provideGeneracionPokemonDao(
        database: AppDatabase
    ): GeneracionPokemonDao = database.generacionPokemonDao()

    @Provides
    fun provideCadenaEvolutivaDao(
        database: AppDatabase
    ): CadenaEvolutivaDao = database.cadenaEvolutivaDao()

    @Provides
    fun provideAtaqueDao(
        database: AppDatabase
    ): AtaqueDao = database.ataqueDao()

    @Provides
    fun provideHabilidadDao(
        database: AppDatabase
    ): HabilidadDao = database.habilidadDao()

    @Provides
    fun provideTipoDao(
        database: AppDatabase
    ): TipoDao = database.tipoDao()
}