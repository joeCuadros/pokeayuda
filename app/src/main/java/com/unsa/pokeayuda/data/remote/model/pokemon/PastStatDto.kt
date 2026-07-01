package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class PastStatDto(
    @SerializedName("generation") val generation: NamedApiResourceDto,
    @SerializedName("stats") val stats: List<StatSlotDto>
)