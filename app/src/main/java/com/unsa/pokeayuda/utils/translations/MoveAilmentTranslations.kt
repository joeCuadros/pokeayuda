package com.unsa.pokeayuda.utils.translations

object MoveAilmentTranslations {
    private val translations = mapOf(
        "unknown" to "Desconocido",
        "none" to "Ninguno",
        "paralysis" to "Parálisis",
        "sleep" to "Sueño",
        "freeze" to "Congelado",
        "burn" to "Quemadura",
        "poison" to "Envenenado",
        "confusion" to "Confusión",
        "infatuation" to "Enamorado",
        "trap" to "Trampa",
        "nightmare" to "Pesadilla",
        "torment" to "Tormento",
        "disable" to "Deshabilitado",
        "yawn" to "Bostezo",
        "heal-block" to "Bloqueo de curación",
        "no-type-immunity" to "Sin inmunidad de tipo",
        "leech-seed" to "Drenadoras",
        "embargo" to "Embargo",
        "perish-song" to "Canción de Pericia",
        "ingrain" to "Raíces"
    )
    fun translate(ailment: String): String {
        return translations[ailment] ?: ailment
    }
}