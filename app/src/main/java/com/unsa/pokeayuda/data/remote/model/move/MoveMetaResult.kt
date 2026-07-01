package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName

data class MoveMetaResult(
    val ailment: String = "none",
    @SerializedName("ailment_chance") val ailmentChance: Int = 0,
    val category: String = "none",
    @SerializedName("crit_rate") val critRate: Int = 0,
    val drain: Int = 0,
    @SerializedName("flinch_chance") val flinchChance: Int = 0,
    val healing: Int = 0,
    @SerializedName("max_hits") val maxHits: Int? = null,
    @SerializedName("max_turns") val maxTurns: Int? = null,
    @SerializedName("min_hits") val minHits: Int? = null,
    @SerializedName("min_turns") val minTurns: Int? = null,
    @SerializedName("stat_chance") val statChance: Int = 0
)