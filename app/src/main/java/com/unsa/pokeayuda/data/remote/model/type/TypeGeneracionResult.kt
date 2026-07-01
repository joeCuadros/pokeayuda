package com.unsa.pokeayuda.data.remote.model.type

import com.google.gson.annotations.SerializedName

data class TypeGeneracionResult(
    val id: Int,
    val name: String,
    @SerializedName("name_es") val nameEs: String,
    @SerializedName("damage_relations") val damageRelations: DamageRelationsDto
)