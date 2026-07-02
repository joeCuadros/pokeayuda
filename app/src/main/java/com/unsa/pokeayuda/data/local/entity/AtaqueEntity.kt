package com.unsa.pokeayuda.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "ataque",
    primaryKeys = ["idAtaque", "idGeneracion"]
)
data class AtaqueEntity(
    val idAtaque: Int,
    val idGeneracion: Int,
    val nombrePokemon: String,
    val nombreGeneracion: String,
    val fecha: Long,
    val data: String
)