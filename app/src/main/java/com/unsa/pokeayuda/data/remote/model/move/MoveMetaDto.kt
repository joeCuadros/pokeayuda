package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class MoveMetaDto(
    @SerializedName("ailment") val ailment: NamedApiResourceDto?,
    @SerializedName("ailment_chance") val ailmentChance: Int,
    @SerializedName("category") val category: NamedApiResourceDto?,
    @SerializedName("crit_rate") val critRate: Int,
    @SerializedName("drain") val drain: Int,
    @SerializedName("flinch_chance") val flinchChance: Int,
    @SerializedName("healing") val healing: Int,
    @SerializedName("max_hits") val maxHits: Int?,
    @SerializedName("max_turns") val maxTurns: Int?,
    @SerializedName("min_hits") val minHits: Int?,
    @SerializedName("min_turns") val minTurns: Int?,
    @SerializedName("stat_chance") val statChance: Int
)