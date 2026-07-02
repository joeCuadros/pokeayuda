package com.unsa.pokeayuda.ui.screens.detail

sealed interface DetailEvent {
    data class Inicializar(val pokemonId: Int) : DetailEvent
    object ActivarCadenaEvolutiva : DetailEvent
    object ActivarAtaques : DetailEvent
    data class SeleccionarJuegoAtaques(val juego: String) : DetailEvent
    data class VerDetalleAtaque(val nombreAtaque: String) : DetailEvent
    object ActivarHabilidades : DetailEvent
    data class VerDetalleHabilidad(val nombreHabilidad: String) : DetailEvent
}