package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MoveStatChangeDto(
    @SerializedName("change") val change: Int,
    @SerializedName("stat") val stat: NamedApiResourceDto
)