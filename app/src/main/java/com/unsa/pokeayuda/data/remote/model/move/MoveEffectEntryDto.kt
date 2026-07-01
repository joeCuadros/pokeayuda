package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MoveEffectEntryDto(
    @SerializedName("short_effect") val shortEffect: String?,
    @SerializedName("effect") val effect: String?,
    @SerializedName("language") val language: NamedApiResourceDto
)