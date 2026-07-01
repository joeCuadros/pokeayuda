package com.unsa.pokeayuda.data.remote.model.type

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class TypeDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("names") val names: List<TypeNameTranslationDto>,
    @SerializedName("damage_relations") val damageRelations: DamageRelationsDto,
    @SerializedName("past_damage_relations") val pastDamageRelations: List<PastDamageRelationsDto>
)