package com.unsa.pokeayuda.ui.screens.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity
import com.unsa.pokeayuda.domain.repository.AppPreferencesRepository
import com.unsa.pokeayuda.domain.repository.EquipoPokemonRepository
import com.unsa.pokeayuda.domain.repository.GeneracionRepository
import com.unsa.pokeayuda.domain.repository.PokemonRepository
import com.unsa.pokeayuda.utils.constants.GenerationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val generacionRepository: GeneracionRepository,
    private val equipoPokemonRepository: EquipoPokemonRepository,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonState())
    val state: StateFlow<PokemonState> = _state.asStateFlow()

    init {
        observarGeneracionYEquipo()
    }

    fun onEvent(event: PokemonEvent) {
        when (event) {
            is PokemonEvent.CambiarBusqueda -> filtrarPokemonDisponibles(event.query)
            is PokemonEvent.AgregarAlEquipo -> agregarAlEquipo(event.idPokemon)
            is PokemonEvent.EliminarDelEquipo -> eliminarDelEquipo(event.id)
            is PokemonEvent.RequerirDetallePokemon -> cargarDetalleEnSegundoPlano(event.idPokemon, event.nombrePokemon)
        }
    }

    private fun observarGeneracionYEquipo() {
        viewModelScope.launch(Dispatchers.IO) {
            appPreferencesRepository.generation.collect { nombreGen ->
                val idGenNullable = GenerationConstants.getId(nombreGen)
                if (idGenNullable == null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Generación no válida: $nombreGen"
                        )
                    }
                    return@collect
                }
                val idGen: Int = idGenNullable
                _state.update {
                    it.copy(
                        isLoading = true,
                        nombreGeneracionActual = nombreGen,
                        idGeneracionActual = idGen
                    )
                }
                try {
                    val disponibles = generacionRepository.getId(idGen, nombreGen)
                    val equipo = equipoPokemonRepository.getAll()
                    _state.update {
                        it.copy(
                            nombresPokemonDisponibles = disponibles,
                            nombresPokemonFiltrados = disponibles,
                            equipoActual = equipo,
                            isLoading = false
                        )
                    }
                    equipo.forEach { pokemon ->
                        val nombreFaltante = disponibles.getOrNull(pokemon.idPokemon - 1) ?: ""
                        if (nombreFaltante.isNotBlank()) {
                            cargarDetalleEnSegundoPlano(pokemon.idPokemon, nombreFaltante)
                        }
                    }

                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.localizedMessage) }
                }
            }
        }
    }

    private fun filtrarPokemonDisponibles(query: String) {
        _state.update { currentState ->
            val filtrados = if (query.isBlank()) {
                currentState.nombresPokemonDisponibles
            } else {
                currentState.nombresPokemonDisponibles.filter {
                    it.contains(query, ignoreCase = true)
                }
            }
            currentState.copy(busquedaTexto = query, nombresPokemonFiltrados = filtrados)
        }
    }

    private fun agregarAlEquipo(idPokemon: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value
            val existe = equipoPokemonRepository.getId(idPokemon)
            if (existe == null) {
                val nuevaEntidad = EquipoPokemonEntity(
                    idPokemon = idPokemon,
                    idGeneracion = currentState.idGeneracionActual
                )
                equipoPokemonRepository.insert(nuevaEntidad)

                val nuevoEquipo = equipoPokemonRepository.getAll()
                _state.update { it.copy(equipoActual = nuevoEquipo) }
                val nombrePokemon = currentState.nombresPokemonDisponibles.getOrNull(idPokemon - 1) ?: ""
                if (nombrePokemon.isNotBlank()) {
                    cargarDetalleEnSegundoPlano(idPokemon, nombrePokemon)
                }
            }
        }
    }

    private fun eliminarDelEquipo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            equipoPokemonRepository.deleteId(id)
            val nuevoEquipo = equipoPokemonRepository.getAll()
            _state.update { currentState ->
                val idPokemonAsociado = currentState.equipoActual.find { it.id == id }?.idPokemon
                val mapaModificado = currentState.detallesPokemonCargados.toMutableMap().apply {
                    if (idPokemonAsociado != null) remove(idPokemonAsociado)
                }
                currentState.copy(equipoActual = nuevoEquipo, detallesPokemonCargados = mapaModificado)
            }
        }
    }

    private fun cargarDetalleEnSegundoPlano(idPokemon: Int, nombrePokemon: String) {
        if (_state.value.detallesPokemonCargados.containsKey(idPokemon)) return

        viewModelScope.launch(Dispatchers.IO) {
            val idGen = _state.value.idGeneracionActual
            val nombreGen = _state.value.nombreGeneracionActual
            val resultado = pokemonRepository.getId(
                idPokemon = idPokemon,
                idGeneracion = idGen,
                nombrePokemon = nombrePokemon,
                nombreGeneracion = nombreGen
            )

            if (resultado != null) {
                _state.update { currentState ->
                    val mapaActualizado = currentState.detallesPokemonCargados.toMutableMap().apply {
                        put(idPokemon, resultado)
                    }
                    currentState.copy(detallesPokemonCargados = mapaActualizado)
                }
            }
        }
    }
}