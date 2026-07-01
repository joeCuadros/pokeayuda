package com.unsa.pokeayuda.utils.translations

object MoveTargetTranslations {
    private val translations = mapOf(
        "specific-move" to "Movimiento específico",
        "selected-pokemon-me-first" to "Pokémon seleccionado (Yo Primero)",
        "ally" to "Aliado",
        "users-field" to "Campo del usuario",
        "user-or-ally" to "Usuario o aliado",
        "opponents-field" to "Campo del oponente",
        "user" to "Usuario",
        "random-opponent" to "Oponente aleatorio",
        "all-other-pokemon" to "Todos los demás Pokémon",
        "selected-pokemon" to "Pokémon seleccionado",
        "all-opponents" to "Todos los oponentes",
        "entire-field" to "Todo el campo",
        "user-and-allies" to "Usuario y aliados",
        "all-pokemon" to "Todos los Pokémon",
        "all-allies" to "Todos los aliados",
        "fainting-pokemon" to "Pokémon debilitado"
    )
    fun translate(target: String): String {
        return translations[target] ?: target
    }
}