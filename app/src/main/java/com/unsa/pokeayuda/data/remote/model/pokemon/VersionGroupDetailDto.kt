package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class VersionGroupDetailDto(
    @SerializedName("level_learned_at") val levelLearnedAt: Int,
    @SerializedName("move_learn_method") val moveLearnMethod: NamedApiResourceDto,
    @SerializedName("version_group") val versionGroup: NamedApiResourceDto
)