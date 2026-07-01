package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class AbilitySlotDto(
    @SerializedName("ability") val ability: NamedApiResourceDto?,
    @SerializedName("is_hidden") val isHidden: Boolean,
    @SerializedName("slot") val slot: Int
)