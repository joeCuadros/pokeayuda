package com.unsa.pokeayuda.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "equipo_pokemon",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["idPokemon"],
            childColumns = ["idPokemon"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idPokemon"])
    ]
)
data class EquipoPokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idPokemon: Int
)