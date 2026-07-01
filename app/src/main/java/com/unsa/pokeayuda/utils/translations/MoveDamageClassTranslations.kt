package com.unsa.pokeayuda.utils.translations

object MoveDamageClassTranslations {
    private val translations = mapOf(
        "status" to "Estado",
        "physical" to "Físico",
        "special" to "Especial"
    )
    fun translate(damageClass: String): String {
        return translations[damageClass] ?: damageClass
    }
}