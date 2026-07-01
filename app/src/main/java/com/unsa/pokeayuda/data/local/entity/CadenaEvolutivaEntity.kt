package com.unsa.pokeayuda.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cadena_evolutiva")
data class CadenaEvolutivaEntity(
    @PrimaryKey
    val id: Int,
    val fecha: Long,
    val data: String
)