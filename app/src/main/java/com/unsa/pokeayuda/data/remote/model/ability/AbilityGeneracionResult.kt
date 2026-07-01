package com.unsa.pokeayuda.data.remote.model.ability

import com.google.gson.annotations.SerializedName

data class AbilityGeneracionResult(
    val id: Int,
    val name: String,
    @SerializedName("name_es") val nameEs: String,
    val effect: String?,
    @SerializedName("short_effect") val shortEffect: String?,
    @SerializedName("flavor_text") val flavorText: List<String>
)