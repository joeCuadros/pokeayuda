package com.unsa.pokeayuda.utils.translations

object MoveCategoryTranslations {
    private val translations = mapOf(
        "damage" to "Daño",
        "ailment" to "Alteración",
        "net-good-stats" to "Mejora de estadísticas",
        "heal" to "Curación",
        "damage-ailment" to "Daño + Alteración",
        "swagger" to "Fanfarroneo",
        "damage-lower" to "Daño + Bajar estadísticas",
        "damage-raise" to "Daño + Subir estadísticas",
        "damage-heal" to "Daño + Curación",
        "ohko" to "KO en un golpe",
        "whole-field-effect" to "Efecto en todo el campo",
        "field-effect" to "Efecto de campo",
        "force-switch" to "Forzar cambio",
        "unique" to "Único"
    )
    fun translate(category: String): String {
        return translations[category] ?: category
    }
}