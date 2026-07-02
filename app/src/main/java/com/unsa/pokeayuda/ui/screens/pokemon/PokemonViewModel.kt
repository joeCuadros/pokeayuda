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
            is PokemonEvent.RequerirDetallePokemon -> cargarDetalleEnSegundoPlano(event.idPokemon)
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
                    generacionRepository.getId(idGen, nombreGen)
                    val disponibles = generacionRepository.getAll()
                    val equipo = equipoPokemonRepository.getByGeneracion(idGen)
                    _state.update {
                        it.copy(
                            nombresPokemonDisponibles = disponibles,
                            nombresPokemonFiltrados = disponibles,
                            equipoActual = equipo,
                            isLoading = false
                        )
                    }
                    equipo.forEach { pokemon ->
                        cargarDetalleEnSegundoPlano(pokemon.idPokemon)
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

    private fun agregarAlEquipo(idPokemon: String) { // Recibe el String (nombre del Pokémon)
        if (idPokemon.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value
            val idGen = currentState.idGeneracionActual
            val nombreGen = currentState.nombreGeneracionActual
            try {
                val pokemonRemoto = pokemonRepository.getId(
                    idPokemon = 0,
                    idGeneracion = idGen,
                    nombrePokemon = idPokemon.lowercase(),
                    nombreGeneracion = nombreGen
                )
                if (pokemonRemoto != null) {
                    val idRealPokemon = pokemonRemoto.id
                    val existe = equipoPokemonRepository.getId(idRealPokemon, idGen)
                    if (existe == null) {
                        val nuevaEntidad = EquipoPokemonEntity(
                            idPokemon = idRealPokemon,
                            idGeneracion = idGen
                        )
                        equipoPokemonRepository.insert(nuevaEntidad)
                        val nuevoEquipo = equipoPokemonRepository.getByGeneracion(idGen)
                        _state.update { currentStateActual ->
                            val mapaActualizado = currentStateActual.detallesPokemonCargados.toMutableMap().apply {
                                put(idRealPokemon, pokemonRemoto)
                            }
                            currentStateActual.copy(
                                equipoActual = nuevoEquipo,
                                detallesPokemonCargados = mapaActualizado
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al agregar Pokémon: ${e.localizedMessage}") }
            }
        }
    }

    private fun eliminarDelEquipo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            equipoPokemonRepository.deleteId(id)
            val nuevoEquipo = equipoPokemonRepository.getByGeneracion(_state.value.idGeneracionActual)
            _state.update { currentState ->
                val idPokemonAsociado = currentState.equipoActual.find { it.id == id }?.idPokemon
                val mapaModificado = currentState.detallesPokemonCargados.toMutableMap().apply {
                    if (idPokemonAsociado != null) remove(idPokemonAsociado)
                }
                currentState.copy(equipoActual = nuevoEquipo, detallesPokemonCargados = mapaModificado)
            }
        }
    }

    private fun cargarDetalleEnSegundoPlano(idPokemon: Int) {
        if (_state.value.detallesPokemonCargados.containsKey(idPokemon)) return

        viewModelScope.launch(Dispatchers.IO) {
            val idGen = _state.value.idGeneracionActual
            val nombreGen = _state.value.nombreGeneracionActual
            val resultado = pokemonRepository.getId(
                idPokemon = idPokemon,
                idGeneracion = idGen,
                nombrePokemon = "",
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