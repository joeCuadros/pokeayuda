package com.unsa.pokeayuda.ui.screens.battle

sealed interface BattleEvent {
    object Inicializar : BattleEvent
    data class CambiarBusquedaRival(val query: String) : BattleEvent
    data class SeleccionarRival(val nombreRival: String) : BattleEvent
    data class OrdenarPorStat(val statName: String) : BattleEvent
}