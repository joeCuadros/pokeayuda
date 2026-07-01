package com.unsa.pokeayuda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generacion_pokemon")
data class GeneracionPokemonEntity(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val fecha: Long,
    val data: String
)