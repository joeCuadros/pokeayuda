package com.unsa.pokeayuda.data.remote.model.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: SpritesDto,
    @SerializedName("abilities") val abilities: List<AbilitySlotDto>,
    @SerializedName("past_abilities") val pastAbilities: List<PastAbilityDto>,
    @SerializedName("stats") val stats: List<StatSlotDto>,
    @SerializedName("past_stats") val pastStats: List<PastStatDto>,
    @SerializedName("types") val types: List<TypeSlotDto>,
    @SerializedName("past_types") val pastTypes: List<PastTypeDto>,
    @SerializedName("moves") val moves: List<MoveSlotDto>
)