package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MoveEffectChangeDto(
    @SerializedName("effect_entries") val effectEntries: List<MoveEffectEntryDto>,
    @SerializedName("version_group") val versionGroup: NamedApiResourceDto
)