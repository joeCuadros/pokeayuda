package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MoveSlotDto(
    @SerializedName("move") val move: NamedApiResourceDto,
    @SerializedName("version_group_details") val versionGroupDetails: List<VersionGroupDetailDto>
)