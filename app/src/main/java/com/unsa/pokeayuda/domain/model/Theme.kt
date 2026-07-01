package com.unsa.pokeayuda.domain.model

enum class Theme(val displayName: String) {
    OCEANO("Océano"),
    BOSQUE("Bosque"),
    ATARDECER("Atardecer"),
    LAVANDA("Lavanda"),
    CORAL("Coral"),
    MEDIANOCHE("Medianoche"),
    ESMERALDA("Esmeralda"),
    RUBI("Rubí"),
    AMBAR("Ámbar"),
    CIELO("Cielo"),
    ROSA("Rosa"),
    GRAFITO("Grafito");

    companion object {
        val Default = OCEANO
    }
}