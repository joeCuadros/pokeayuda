package com.unsa.pokeayuda.utils.translations

object StatTranslations {
    private val translations = mapOf(
        "hp" to "PS",
        "attack" to "Ataque",
        "defense" to "Defensa",
        "special-attack" to "Ataque Especial",
        "special-defense" to "Defensa Especial",
        "speed" to "Velocidad",
        "accuracy" to "Precisión",
        "evasion" to "Evasión",
        "special" to "Especial"
    )
    fun translate(stat: String): String {
        return translations[stat] ?: stat
    }
}