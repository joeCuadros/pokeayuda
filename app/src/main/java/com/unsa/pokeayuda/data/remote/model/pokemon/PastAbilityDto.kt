package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class PastAbilityDto(
    @SerializedName("generation") val generation: NamedApiResourceDto,
    @SerializedName("abilities") val abilities: List<AbilitySlotDto>
)