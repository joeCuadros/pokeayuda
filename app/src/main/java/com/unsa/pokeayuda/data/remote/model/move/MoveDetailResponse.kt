package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MoveDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("names") val names: List<MoveNameTranslationDto>,
    @SerializedName("accuracy") val accuracy: Int?,
    @SerializedName("power") val power: Int?,
    @SerializedName("pp") val pp: Int?,
    @SerializedName("priority") val priority: Int,
    @SerializedName("effect_chance") val effectChance: Int?,
    @SerializedName("damage_class") val damageClass: NamedApiResourceDto,
    @SerializedName("target") val target: NamedApiResourceDto,
    @SerializedName("type") val type: NamedApiResourceDto,
    @SerializedName("meta") val meta: MoveMetaDto?,
    @SerializedName("stat_changes") val statChanges: List<MoveStatChangeDto>,
    @SerializedName("effect_entries") val effectEntries: List<MoveEffectEntryDto>,
    @SerializedName("past_values") val pastValues: List<MovePastValueDto>,
    @SerializedName("effect_changes") val effectChanges: List<MoveEffectChangeDto>,
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<MoveFlavorTextEntryDto>
)