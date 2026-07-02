package com.unsa.pokeayuda.utils

object PokemonTypeVS {
    data class GeneracionData(
        val tipos: List<String>,
        val matrizDefensiva: Map<String, Map<String, Float>>
    )

    // --- 1. MATRIZ GENERACIÓN I (Sin Acero, Siniestro ni Hada. Glitch de Fantasma inútil vs Psíquico incluido) ---
    private val matrizGen1 = mapOf(
        "normal" to mapOf("fighting" to 2.0f, "ghost" to 0.0f),
        "fire" to mapOf("water" to 2.0f, "ground" to 2.0f, "rock" to 2.0f, "fire" to 0.5f, "grass" to 0.5f, "bug" to 0.5f),
        "water" to mapOf("grass" to 2.0f, "electric" to 2.0f, "fire" to 0.5f, "water" to 0.5f, "ice" to 0.5f),
        "grass" to mapOf("fire" to 2.0f, "ice" to 2.0f, "poison" to 2.0f, "flying" to 2.0f, "bug" to 2.0f, "water" to 0.5f, "electric" to 0.5f, "grass" to 0.5f, "ground" to 0.5f),
        "electric" to mapOf("ground" to 2.0f, "electric" to 0.5f, "flying" to 0.5f),
        "ice" to mapOf("fire" to 2.0f, "fighting" to 2.0f, "rock" to 2.0f, "ice" to 0.5f), // En Gen 1, Fuego no resistía a Hielo
        "fighting" to mapOf("flying" to 2.0f, "psychic" to 2.0f, "bug" to 0.5f, "rock" to 0.5f),
        "poison" to mapOf("ground" to 2.0f, "psychic" to 2.0f, "bug" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "poison" to 0.5f), // Gen 1: Bicho x2 a Veneno
        "ground" to mapOf("water" to 2.0f, "grass" to 2.0f, "ice" to 2.0f, "poison" to 0.5f, "rock" to 0.5f, "electric" to 0.0f),
        "flying" to mapOf("electric" to 2.0f, "ice" to 2.0f, "rock" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "bug" to 0.5f, "ground" to 0.0f),
        "psychic" to mapOf("bug" to 2.0f, "ghost" to 0.0f), // Gen 1 Glitch: Fantasma no afectaba a Psíquico
        "bug" to mapOf("fire" to 2.0f, "flying" to 2.0f, "rock" to 2.0f, "poison" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "ground" to 0.5f), // Gen 1: Veneno x2 a Bicho
        "rock" to mapOf("water" to 2.0f, "grass" to 2.0f, "fighting" to 2.0f, "ground" to 2.0f, "normal" to 0.5f, "fire" to 0.5f, "poison" to 0.5f, "flying" to 0.5f),
        "ghost" to mapOf("ghost" to 2.0f, "normal" to 0.0f, "fighting" to 0.0f),
        "dragon" to mapOf("ice" to 2.0f, "dragon" to 2.0f, "fire" to 0.5f, "water" to 0.5f, "electric" to 0.5f, "grass" to 0.5f)
    )

    // --- 2. MATRIZ GENERACIONES II a V (Se agregan Acero y Siniestro. Acero resiste Fantasma/Siniestro) ---
    private val matrizGen2a5 = mapOf(
        "normal" to mapOf("fighting" to 2.0f, "ghost" to 0.0f),
        "fire" to mapOf("water" to 2.0f, "ground" to 2.0f, "rock" to 2.0f, "fire" to 0.5f, "grass" to 0.5f, "ice" to 0.5f, "bug" to 0.5f, "steel" to 0.5f),
        "water" to mapOf("grass" to 2.0f, "electric" to 2.0f, "fire" to 0.5f, "water" to 0.5f, "ice" to 0.5f, "steel" to 0.5f),
        "grass" to mapOf("fire" to 2.0f, "ice" to 2.0f, "poison" to 2.0f, "flying" to 2.0f, "bug" to 2.0f, "water" to 0.5f, "electric" to 0.5f, "grass" to 0.5f, "ground" to 0.5f),
        "electric" to mapOf("ground" to 2.0f, "electric" to 0.5f, "flying" to 0.5f, "steel" to 0.5f),
        "ice" to mapOf("fire" to 2.0f, "fighting" to 2.0f, "rock" to 2.0f, "steel" to 2.0f, "ice" to 0.5f),
        "fighting" to mapOf("flying" to 2.0f, "psychic" to 2.0f, "bug" to 0.5f, "rock" to 0.5f, "dark" to 0.5f),
        "poison" to mapOf("ground" to 2.0f, "psychic" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "poison" to 0.5f, "bug" to 0.5f),
        "ground" to mapOf("water" to 2.0f, "grass" to 2.0f, "ice" to 2.0f, "poison" to 0.5f, "rock" to 0.5f, "electric" to 0.0f),
        "flying" to mapOf("electric" to 2.0f, "ice" to 2.0f, "rock" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "bug" to 0.5f, "ground" to 0.0f),
        "psychic" to mapOf("bug" to 2.0f, "ghost" to 2.0f, "dark" to 2.0f, "fighting" to 0.5f, "psychic" to 0.5f),
        "bug" to mapOf("fire" to 2.0f, "flying" to 2.0f, "rock" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "ground" to 0.5f),
        "rock" to mapOf("water" to 2.0f, "grass" to 2.0f, "fighting" to 2.0f, "ground" to 2.0f, "steel" to 2.0f, "normal" to 0.5f, "fire" to 0.5f, "poison" to 0.5f, "flying" to 0.5f),
        "ghost" to mapOf("ghost" to 2.0f, "dark" to 2.0f, "poison" to 0.5f, "bug" to 0.5f, "normal" to 0.0f, "fighting" to 0.0f),
        "dragon" to mapOf("ice" to 2.0f, "dragon" to 2.0f, "fire" to 0.5f, "water" to 0.5f, "electric" to 0.5f, "grass" to 0.5f),
        "steel" to mapOf("fire" to 2.0f, "fighting" to 2.0f, "ground" to 2.0f, "normal" to 0.5f, "grass" to 0.5f, "ice" to 0.5f, "flying" to 0.5f, "psychic" to 0.5f, "bug" to 0.5f, "rock" to 0.5f, "dragon" to 0.5f, "steel" to 0.5f, "poison" to 0.0f, "ghost" to 0.5f, "dark" to 0.5f), // Acero resistía Fantasma/Siniestro
        "dark" to mapOf("fighting" to 2.0f, "bug" to 2.0f, "ghost" to 0.5f, "dark" to 0.5f, "psychic" to 0.0f)
    )

    // --- 3. MATRIZ GENERACIÓN VI+ (Se agrega Hada. Acero PIERDE resistencias a Fantasma y Siniestro) ---
    private val matrizGen6Plus = mapOf(
        "normal" to mapOf("fighting" to 2.0f, "ghost" to 0.0f),
        "fire" to mapOf("water" to 2.0f, "ground" to 2.0f, "rock" to 2.0f, "fire" to 0.5f, "grass" to 0.5f, "ice" to 0.5f, "bug" to 0.5f, "steel" to 0.5f, "fairy" to 0.5f),
        "water" to mapOf("grass" to 2.0f, "electric" to 2.0f, "fire" to 0.5f, "water" to 0.5f, "ice" to 0.5f, "steel" to 0.5f),
        "grass" to mapOf("fire" to 2.0f, "ice" to 2.0f, "poison" to 2.0f, "flying" to 2.0f, "bug" to 2.0f, "water" to 0.5f, "electric" to 0.5f, "grass" to 0.5f, "ground" to 0.5f),
        "electric" to mapOf("ground" to 2.0f, "electric" to 0.5f, "flying" to 0.5f, "steel" to 0.5f),
        "ice" to mapOf("fire" to 2.0f, "fighting" to 2.0f, "rock" to 2.0f, "steel" to 2.0f, "ice" to 0.5f),
        "fighting" to mapOf("flying" to 2.0f, "psychic" to 2.0f, "fairy" to 2.0f, "bug" to 0.5f, "rock" to 0.5f, "dark" to 0.5f),
        "poison" to mapOf("ground" to 2.0f, "psychic" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "poison" to 0.5f, "bug" to 0.5f, "fairy" to 0.5f),
        "ground" to mapOf("water" to 2.0f, "grass" to 2.0f, "ice" to 2.0f, "poison" to 0.5f, "rock" to 0.5f, "electric" to 0.0f),
        "flying" to mapOf("electric" to 2.0f, "ice" to 2.0f, "rock" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "bug" to 0.5f, "ground" to 0.0f),
        "psychic" to mapOf("bug" to 2.0f, "ghost" to 2.0f, "dark" to 2.0f, "fighting" to 0.5f, "psychic" to 0.5f),
        "bug" to mapOf("fire" to 2.0f, "flying" to 2.0f, "rock" to 2.0f, "grass" to 0.5f, "fighting" to 0.5f, "ground" to 0.5f),
        "rock" to mapOf("water" to 2.0f, "grass" to 2.0f, "fighting" to 2.0f, "ground" to 2.0f, "steel" to 2.0f, "normal" to 0.5f, "fire" to 0.5f, "poison" to 0.5f, "flying" to 0.5f),
        "ghost" to mapOf("ghost" to 2.0f, "dark" to 2.0f, "poison" to 0.5f, "bug" to 0.5f, "normal" to 0.0f, "fighting" to 0.0f),
        "dragon" to mapOf("ice" to 2.0f, "dragon" to 2.0f, "fairy" to 2.0f, "fire" to 0.5f, "water" to 0.5f, "electric" to 0.5f, "grass" to 0.5f),
        "steel" to mapOf("fire" to 2.0f, "fighting" to 2.0f, "ground" to 2.0f, "normal" to 0.5f, "grass" to 0.5f, "ice" to 0.5f, "flying" to 0.5f, "psychic" to 0.5f, "bug" to 0.5f, "rock" to 0.5f, "dragon" to 0.5f, "steel" to 0.5f, "fairy" to 0.5f, "poison" to 0.0f), // Pierde resistencia a Siniestro/Fantasma
        "dark" to mapOf("fighting" to 2.0f, "bug" to 2.0f, "fairy" to 2.0f, "ghost" to 0.5f, "dark" to 0.5f, "psychic" to 0.0f),
        "fairy" to mapOf("poison" to 2.0f, "steel" to 2.0f, "fighting" to 0.5f, "bug" to 0.5f, "dark" to 0.5f, "dragon" to 0.0f)
    )

    // --- DICCIONARIO PRINCIPAL INDEXADO POR EL ID/KEY DE LA GENERACIÓN ---
    val generaciones: Map<String, GeneracionData> = mapOf(
        "generation-i" to GeneracionData(matrizGen1.keys.toList(), matrizGen1),
        "generation-ii" to GeneracionData(matrizGen2a5.keys.toList(), matrizGen2a5),
        "generation-iii" to GeneracionData(matrizGen2a5.keys.toList(), matrizGen2a5),
        "generation-iv" to GeneracionData(matrizGen2a5.keys.toList(), matrizGen2a5),
        "generation-v" to GeneracionData(matrizGen2a5.keys.toList(), matrizGen2a5),
        "generation-vi" to GeneracionData(matrizGen6Plus.keys.toList(), matrizGen6Plus),
        "generation-vii" to GeneracionData(matrizGen6Plus.keys.toList(), matrizGen6Plus),
        "generation-viii" to GeneracionData(matrizGen6Plus.keys.toList(), matrizGen6Plus),
        "generation-ix" to GeneracionData(matrizGen6Plus.keys.toList(), matrizGen6Plus)
    )

    fun calcularDebilidades(genKey: String, tiposDefensores: List<String>): Map<String, Float> {
        val dataGen = generaciones[genKey.lowercase().trim()] ?: generaciones["generacion-i"]!!
        val matrizDaño = dataGen.tipos.associateWith { 1.0f }.toMutableMap()
        for (tipoDefensor in tiposDefensores) {
            val modificadores = dataGen.matrizDefensiva[tipoDefensor.lowercase().trim()] ?: emptyMap()
            for ((tipoAtacante, multiplicador) in modificadores) {
                if (matrizDaño.containsKey(tipoAtacante)) {
                    matrizDaño[tipoAtacante] = matrizDaño[tipoAtacante]!! * multiplicador
                }
            }
        }
        return matrizDaño
    }
}