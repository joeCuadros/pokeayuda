package com.unsa.pokeayuda.data.remote.model.ability

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class AbilityEffectChangeDto(
    @SerializedName("effect_entries") val effectEntries: List<AbilityEffectEntryDto>,
    @SerializedName("version_group") val versionGroup: NamedApiResourceDto
)