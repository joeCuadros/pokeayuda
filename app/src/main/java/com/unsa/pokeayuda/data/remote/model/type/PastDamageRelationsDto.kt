package com.unsa.pokeayuda.data.remote.model.type

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class PastDamageRelationsDto(
    @SerializedName("generation") val generation: NamedApiResourceDto,
    @SerializedName("damage_relations") val damageRelations: DamageRelationsDto
)