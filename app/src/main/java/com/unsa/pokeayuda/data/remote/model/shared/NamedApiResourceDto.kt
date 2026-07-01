package com.unsa.pokeayuda.data.remote.model.shared

import com.google.gson.annotations.SerializedName

data class NamedApiResourceDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)