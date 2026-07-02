package com.unsa.pokeayuda.ui.screens.battle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.pokeayuda.data.remote.model.pokemon.PokemonGeneracionResult
import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult
import com.unsa.pokeayuda.domain.repository.AppPreferencesRepository
import com.unsa.pokeayuda.domain.repository.EquipoPokemonRepository
import com.unsa.pokeayuda.domain.repository.GeneracionRepository
import com.unsa.pokeayuda.domain.repository.PokemonRepository
import com.unsa.pokeayuda.domain.repository.TipoRepository
import com.unsa.pokeayuda.utils.constants.GenerationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val generacionRepository: GeneracionRepository,
    private val equipoPokemonRepository: EquipoPokemonRepository,
    private val pokemonRepository: PokemonRepository,
    private val tipoRepository: TipoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BattleState())
    val state: StateFlow<BattleState> = _state.asStateFlow()

    fun onEvent(event: BattleEvent) {
        when (event) {
            BattleEvent.Inicializar -> inicializarCombate()
            is BattleEvent.CambiarBusquedaRival -> filtrarRivales(event.query)
            is BattleEvent.SeleccionarRival -> cargarRival(event.nombreRival)
            is BattleEvent.OrdenarPorStat -> ordenarEstadisticas(event.statName)
        }
    }

    private fun inicializarCombate() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val genNombre = appPreferencesRepository.generation.first()
                val idGen = GenerationConstants.getId(genNombre) ?: 0

                generacionRepository.getId(idGen, genNombre)
                val disponibles = generacionRepository.getAll()

                val equipoEntities = equipoPokemonRepository.getByGeneracion(idGen)
                val listaEquipoFull = equipoEntities.mapNotNull { entidad ->
                    pokemonRepository.getId(
                        idPokemon = entidad.idPokemon,
                        idGeneracion = idGen,
                        nombrePokemon = "",
                        nombreGeneracion = genNombre
                    )
                }

                _state.update { it.copy(
                    nombreGeneracionActual = genNombre,
                    idGeneracionActual = idGen,
                    nombresPokemonDisponibles = disponibles,
                    nombresPokemonFiltrados = disponibles,
                    equipoPokemon = listaEquipoFull,
                    isLoading = false
                )}

                actualizarTablaStats()
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.localizedMessage)}
            }
        }
    }

    private fun filtrarRivales(query: String) {
        val actualesDisponibles = _state.value.nombresPokemonDisponibles
        val filtrados = if (query.isBlank()) {
            actualesDisponibles
        } else {
            actualesDisponibles.filter { it.contains(query, ignoreCase = true) }
        }
        _state.update { it.copy(busquedaRivalTexto = query, nombresPokemonFiltrados = filtrados)}
    }

    private fun cargarRival(nombreRival: String) {
        if (nombreRival.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingRival = true)}
            try {
                val currentState = _state.value
                val rivalData = pokemonRepository.getId(
                    idPokemon = 0,
                    idGeneracion = currentState.idGeneracionActual,
                    nombrePokemon = nombreRival.lowercase(),
                    nombreGeneracion = currentState.nombreGeneracionActual
                )
                _state.update { it.copy(rivalPokemon = rivalData, isLoadingRival = false)}

                actualizarTablaStats()
                calcularMatricesEfectividad()
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingRival = false, error = e.localizedMessage)}
            }
        }
    }

    private fun actualizarTablaStats() {
        val rows = mutableListOf<PokemonStatRow>()
        val currentState = _state.value

        currentState.equipoPokemon.forEach { p ->
            rows.add(mapearAStatRow(p, esRival = false))
        }

        currentState.rivalPokemon?.let { r ->
            rows.add(mapearAStatRow(r, esRival = true))
        }

        _state.update { it.copy(tablaStats = rows)}

        _state.value.statOrdenadoPor?.let { ordenarEstadisticas(it, mantenerDireccion = true) }
    }

    private fun mapearAStatRow(pokemon: PokemonGeneracionResult, esRival: Boolean): PokemonStatRow {
        val statsMap = pokemon.stats.associate { it.stat.name to it.baseStat }
        return PokemonStatRow(
            id = pokemon.id,
            nombre = pokemon.name,
            esRival = esRival,
            stats = statsMap
        )
    }

    private fun ordenarEstadisticas(statName: String, mantenerDireccion: Boolean = false) {
        val currentState = _state.value
        val nuevaDireccion = if (mantenerDireccion) currentState.statOrdenAscendente else {
            if (currentState.statOrdenadoPor == statName) !currentState.statOrdenAscendente else false
        }

        val listaOrdenada = currentState.tablaStats.toMutableList()
        val selector: (PokemonStatRow) -> Int = { r -> r.stats[statName] ?: 0 }

        if (nuevaDireccion) {
            listaOrdenada.sortBy(selector)
        } else {
            listaOrdenada.sortByDescending(selector)
        }

        _state.update { it.copy(
            tablaStats = listaOrdenada,
            statOrdenadoPor = statName,
            statOrdenAscendente = nuevaDireccion
        )}
    }

    private fun calcularMatricesEfectividad() {
        val currentState = _state.value
        val rival = currentState.rivalPokemon ?: return
        if (currentState.equipoPokemon.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val idGen = currentState.idGeneracionActual
                val genNombre = currentState.nombreGeneracionActual

                val listaTiposRivales = rival.types.mapNotNull {
                    tipoRepository.getId(0, idGen, it.type.name, genNombre)
                }
                val rivalTiposString = rival.types.joinToString(" / ") { it.type.name }

                val listaMatchups = currentState.equipoPokemon.map { aliado ->
                    val listaTiposAliados = aliado.types.mapNotNull {
                        tipoRepository.getId(0, idGen, it.type.name, genNombre)
                    }
                    val aliadoTiposString = aliado.types.joinToString(" / ") { it.type.name }

                    val rowsOfensiva = listaTiposAliados.map { tipoAliado ->
                        var mult = 1.0f
                        rival.types.forEach { slotRival ->
                            mult *= obtenerMultiplicador(tipoAliado, slotRival.type.name)
                        }
                        EfectividadRow(
                            tipoAtacante = tipoAliado.name,
                            tipoDefensorCombinado = rivalTiposString,
                            multiplicador = mult
                        )
                    }

                    val rowsDefensiva = listaTiposRivales.map { tipoRival ->
                        var mult = 1.0f
                        aliado.types.forEach { slotAliado ->
                            mult *= obtenerMultiplicador(tipoRival, slotAliado.type.name)
                        }
                        EfectividadRow(
                            tipoAtacante = tipoRival.name,
                            tipoDefensorCombinado = aliadoTiposString,
                            multiplicador = mult
                        )
                    }

                    PokemonEfectividadMatchup(
                        idPokemon = aliado.id,
                        nombrePokemon = aliado.name,
                        tablaOfensiva = rowsOfensiva,
                        tablaDefensiva = rowsDefensiva
                    )
                }

                _state.update { it.copy(matrizEfectividadEquipo = listaMatchups)}
            } catch (_: Exception) {}
        }
    }

    private fun obtenerMultiplicador(tipoAtaque: TypeGeneracionResult, tipoDefensa: String): Float {
        val rel = tipoAtaque.damageRelations
        return when {
            rel.doubleDamageTo.any { it.name == tipoDefensa } -> 2.0f
            rel.halfDamageTo.any { it.name == tipoDefensa } -> 0.5f
            rel.noDamageTo.any { it.name == tipoDefensa } -> 0.0f
            else -> 1.0f
        }
    }
}