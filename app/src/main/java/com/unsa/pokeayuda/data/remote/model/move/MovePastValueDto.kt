package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MovePastValueDto(
    @SerializedName("accuracy") val accuracy: Int?,
    @SerializedName("power") val power: Int?,
    @SerializedName("pp") val pp: Int?,
    @SerializedName("effect_chance") val effectChance: Int?,
    @SerializedName("type") val type: NamedApiResourceDto?,
    @SerializedName("version_group") val versionGroup: NamedApiResourceDto
)