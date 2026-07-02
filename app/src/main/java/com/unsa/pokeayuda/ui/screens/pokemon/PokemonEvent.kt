package com.unsa.pokeayuda.ui.screens.pokemon

sealed interface PokemonEvent {
    data class CambiarBusqueda(val query: String) : PokemonEvent
    data class AgregarAlEquipo(val idPokemon: String) : PokemonEvent
    data class EliminarDelEquipo(val id: Int) : PokemonEvent
    data class RequerirDetallePokemon(val idPokemon: Int) : PokemonEvent
}