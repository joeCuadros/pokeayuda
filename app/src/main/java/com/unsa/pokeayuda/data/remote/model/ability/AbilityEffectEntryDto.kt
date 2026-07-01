package com.unsa.pokeayuda.data.remote.model.ability

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class AbilityEffectEntryDto(
    @SerializedName("effect") val effect: String?,
    @SerializedName("short_effect") val shortEffect: String?,
    @SerializedName("language") val language: NamedApiResourceDto
)