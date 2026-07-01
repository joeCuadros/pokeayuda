package com.unsa.pokeayuda.utils.translations

object TypeTranslations {
    private val translations = mapOf(
        "normal" to "Normal",
        "fighting" to "Lucha",
        "flying" to "Volador",
        "poison" to "Veneno",
        "ground" to "Tierra",
        "rock" to "Roca",
        "bug" to "Bicho",
        "ghost" to "Fantasma",
        "steel" to "Acero",
        "fire" to "Fuego",
        "water" to "Agua",
        "grass" to "Planta",
        "electric" to "Eléctrico",
        "psychic" to "Psíquico",
        "ice" to "Hielo",
        "dragon" to "Dragón",
        "dark" to "Siniestro",
        "fairy" to "Hada",
        "stellar" to "Estelar",
        "unknown" to "Desconocido",
        "shadow" to "Sombra"
    )
    fun translate(type: String): String {
        return translations[type] ?: type
    }
}