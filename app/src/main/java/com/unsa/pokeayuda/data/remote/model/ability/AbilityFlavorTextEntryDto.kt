package com.unsa.pokeayuda.data.remote.model.ability

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class AbilityFlavorTextEntryDto(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val language: NamedApiResourceDto,
    @SerializedName("version_group") val versionGroup: NamedApiResourceDto
)