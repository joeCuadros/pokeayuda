package com.unsa.pokeayuda.data.remote.model.move

import com.google.gson.annotations.SerializedName

data class MoveGeneracionResult(
    val id: Int,
    val name: String,
    @SerializedName("name_es") val nameEs: String,
    val accuracy: Int?,
    val power: Int?,
    val pp: Int?,
    val priority: Int,
    @SerializedName("effect_chance") val effectChance: Int?,
    @SerializedName("damage_class") val damageClass: String,
    val target: String,
    val type: String,
    val meta: MoveMetaResult,
    @SerializedName("stat_changes") val statChanges: List<MoveStatChangeDto>,
    val effect: String?,
    @SerializedName("name_flavor_text") val nameFlavorText: List<String>
)