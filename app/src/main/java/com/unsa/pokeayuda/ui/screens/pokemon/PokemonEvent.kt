package com.unsa.pokeayuda.ui.screens.pokemon

import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity

sealed interface PokemonEvent {
    data class CambiarBusqueda(val query: String) : PokemonEvent
    data class AgregarAlEquipo(val idPokemon: Int) : PokemonEvent
    data class EliminarDelEquipo(val id: Int) : PokemonEvent
    data class RequerirDetallePokemon(val idPokemon: Int, val nombrePokemon: String) : PokemonEvent
}