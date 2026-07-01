package com.unsa.pokeayuda.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "habilidad",
    primaryKeys = ["idPokemon", "idGeneracion"]
)
data class HabilidadEntity(
    val idPokemon: Int,
    val idGeneracion: Int,
    val nombrePokemon: String,
    val nombreGeneracion: String,
    val fecha: Long,
    val data: String
)