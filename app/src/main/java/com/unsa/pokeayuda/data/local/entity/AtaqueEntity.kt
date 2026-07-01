package com.unsa.pokeayuda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ataque")
data class AtaqueEntity(
    @PrimaryKey
    val id: Int,
    val idPokemon: Int,
    val idGeneracion: Int,
    val nombrePokemon: String,
    val nombreGeneracion: String,
    val fecha: Long,
    val data: String
)