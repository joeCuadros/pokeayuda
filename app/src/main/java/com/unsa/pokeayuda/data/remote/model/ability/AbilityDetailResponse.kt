package com.unsa.pokeayuda.data.remote.model.ability

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class AbilityDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("names") val names: List<AbilityNameTranslationDto>,
    @SerializedName("effect_entries") val effectEntries: List<AbilityEffectEntryDto>,
    @SerializedName("effect_changes") val effectChanges: List<AbilityEffectChangeDto>,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<AbilityFlavorTextEntryDto>
)