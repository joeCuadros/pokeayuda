package com.unsa.pokeayuda.data.remote.model.type

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class TypeNameTranslationDto(
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: NamedApiResourceDto
)