package com.unsa.pokeayuda.data.remote.model.type

import com.google.gson.annotations.SerializedName
import com.unsa.pokeayuda.data.remote.model.shared.NamedApiResourceDto

data class DamageRelationsDto(
    @SerializedName("double_damage_from") val doubleDamageFrom: List<NamedApiResourceDto>,
    @SerializedName("double_damage_to") val doubleDamageTo: List<NamedApiResourceDto>,
    @SerializedName("half_damage_from") val halfDamageFrom: List<NamedApiResourceDto>,
    @SerializedName("half_damage_to") val halfDamageTo: List<NamedApiResourceDto>,
    @SerializedName("no_damage_from") val noDamageFrom: List<NamedApiResourceDto>,
    @SerializedName("no_damage_to") val noDamageTo: List<NamedApiResourceDto>
) {
    fun deepCopy(): DamageRelationsDto {
        return DamageRelationsDto(
            doubleDamageFrom = this.doubleDamageFrom.map { it.copy() },
            doubleDamageTo = this.doubleDamageTo.map { it.copy() },
            halfDamageFrom = this.halfDamageFrom.map { it.copy() },
            halfDamageTo = this.halfDamageTo.map { it.copy() },
            noDamageFrom = this.noDamageFrom.map { it.copy() },
            noDamageTo = this.noDamageTo.map { it.copy() }
        )
    }
}