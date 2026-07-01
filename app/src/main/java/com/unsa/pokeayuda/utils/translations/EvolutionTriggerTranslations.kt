package com.unsa.pokeayuda.utils.translations

object EvolutionTriggerTranslations {
    private val translations = mapOf(
        "level-up" to "Subir de nivel",
        "trade" to "Intercambio",
        "use-item" to "Usar objeto",
        "shed" to "Muda",
        "spin" to "Girar",
        "tower-of-darkness" to "Torre de la Oscuridad",
        "tower-of-waters" to "Torre de las Aguas",
        "three-critical-hits" to "Tres golpes críticos",
        "take-damage" to "Recibir daño",
        "other" to "Otro",
        "agile-style-move" to "Movimiento estilo ágil",
        "strong-style-move" to "Movimiento estilo fuerte",
        "recoil-damage" to "Daño por retroceso",
        "use-move" to "Usar movimiento",
        "three-defeated-bisharp" to "Tres Bisharp derrotados",
        "gimmighoul-coins" to "Monedas de Gimmighoul"
    )
    fun translate(trigger: String): String {
        return translations[trigger] ?: trigger
    }
}