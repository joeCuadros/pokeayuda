package com.unsa.pokeayuda.data.remote.model.evolution

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class EvolutionDetailDto(
    @SerializedName("trigger") val trigger: NamedApiResourceDto?,
    @SerializedName("min_level") val minLevel: Int?,
    @SerializedName("item") val item: NamedApiResourceDto?,
    @SerializedName("version_group") val versionGroup: NamedApiResourceDto?
)