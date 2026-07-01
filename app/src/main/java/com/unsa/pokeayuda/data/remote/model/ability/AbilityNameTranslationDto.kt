package com.unsa.pokeayuda.data.remote.model.ability

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class AbilityNameTranslationDto(
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: NamedApiResourceDto
)