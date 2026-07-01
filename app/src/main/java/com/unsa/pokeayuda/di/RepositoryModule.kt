package com.unsa.pokeayuda.di

import com.unsa.pokeayuda.data.repository.AppPreferencesRepositoryImpl
import com.unsa.pokeayuda.data.repository.AtaqueRepositoryImpl
import com.unsa.pokeayuda.data.repository.CadenaEvolutivaRepositoryImpl
import com.unsa.pokeayuda.data.repository.EquipoPokemonRepositoryImpl
import com.unsa.pokeayuda.data.repository.GeneracionRepositoryImpl
import com.unsa.pokeayuda.data.repository.HabilidadRepositoryImpl
import com.unsa.pokeayuda.data.repository.PokemonRepositoryImpl
import com.unsa.pokeayuda.data.repository.TipoRepositoryImpl
import com.unsa.pokeayuda.domain.repository.AppPreferencesRepository
import com.unsa.pokeayuda.domain.repository.AtaqueRepository
import com.unsa.pokeayuda.domain.repository.CadenaEvolutivaRepository
import com.unsa.pokeayuda.domain.repository.EquipoPokemonRepository
import com.unsa.pokeayuda.domain.repository.GeneracionRepository
import com.unsa.pokeayuda.domain.repository.HabilidadRepository
import com.unsa.pokeayuda.domain.repository.PokemonRepository
import com.unsa.pokeayuda.domain.repository.TipoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindGeneracionRepository(
        impl: GeneracionRepositoryImpl
    ): GeneracionRepository

    @Binds
    @Singleton
    abstract fun bindCadenaEvolutivaRepository(
        impl: CadenaEvolutivaRepositoryImpl
    ): CadenaEvolutivaRepository

    @Binds
    @Singleton
    abstract fun bindAtaqueRepository(
        impl: AtaqueRepositoryImpl
    ): AtaqueRepository

    @Binds
    @Singleton
    abstract fun bindHabilidadRepository(
        impl: HabilidadRepositoryImpl
    ): HabilidadRepository

    @Binds
    @Singleton
    abstract fun bindTipoRepository(
        impl: TipoRepositoryImpl
    ): TipoRepository

    @Binds
    @Singleton
    abstract fun bindEquipoPokemonRepository(
        impl: EquipoPokemonRepositoryImpl
    ): EquipoPokemonRepository

    @Binds
    @Singleton
    abstract fun bindAppPreferencesRepository(
        impl: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository
}