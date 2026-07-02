package com.unsa.pokeayuda.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.pokeayuda.data.remote.model.ability.AbilityGeneracionResult
import com.unsa.pokeayuda.data.remote.model.move.MoveGeneracionResult
import com.unsa.pokeayuda.data.remote.model.type.TypeGeneracionResult
import com.unsa.pokeayuda.domain.repository.AppPreferencesRepository
import com.unsa.pokeayuda.domain.repository.AtaqueRepository
import com.unsa.pokeayuda.domain.repository.CadenaEvolutivaRepository
import com.unsa.pokeayuda.domain.repository.HabilidadRepository
import com.unsa.pokeayuda.domain.repository.PokemonRepository
import com.unsa.pokeayuda.domain.repository.TipoRepository
import com.unsa.pokeayuda.utils.constants.GenerationConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val pokemonRepository: PokemonRepository,
    private val cadenaEvolutivaRepository: CadenaEvolutivaRepository,
    private val ataqueRepository: AtaqueRepository,
    private val habilidadRepository: HabilidadRepository,
    private val tipoRepository: TipoRepository
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.Inicializar -> {
                state = state.copy(pokemonId = event.pokemonId)
                cargarDatosPokemonBase(event.pokemonId)
            }
            DetailEvent.ActivarCadenaEvolutiva -> {
                cargarCadenaEvolutiva()
            }
            DetailEvent.ActivarAtaques -> {
                cargarAtaques()
            }
            is DetailEvent.SeleccionarJuegoAtaques -> {
                state = state.copy(juegoSeleccionado = event.juego)
            }
            is DetailEvent.VerDetalleAtaque -> {
                cargarDetalleEspecificoAtaque(event.nombreAtaque)
            }
            DetailEvent.ActivarHabilidades -> {
                cargarHabilidades()
            }
            is DetailEvent.VerDetalleHabilidad -> {
                cargarDetalleEspecificoHabilidad(event.nombreHabilidad)
            }
            DetailEvent.ActivarMatrizTipos -> {
                cargarMatrizTipos()
            }
        }
    }

    private fun cargarDatosPokemonBase(pokemonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoadingPokemon = true, errorPokemon = null)
            try {
                val genNombre = appPreferencesRepository.generation.first()
                val idGen = GenerationConstants.getId(genNombre) ?: 0

                val resultado = pokemonRepository.getId(
                    idPokemon = pokemonId,
                    idGeneracion = idGen,
                    nombrePokemon = "",
                    nombreGeneracion = genNombre
                )

                state = state.copy(
                    nombreGeneracionActual = genNombre,
                    pokemonDetalle = resultado,
                    isLoadingPokemon = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoadingPokemon = false,
                    errorPokemon = e.localizedMessage ?: "Error al cargar datos base"
                )
            }
        }
    }

    private fun cargarCadenaEvolutiva() {
        if (state.evolucionDetalle != null || state.isLoadingEvoluciones) return

        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoadingEvoluciones = true, errorEvoluciones = null)
            try {
                val pokemonNombre = state.pokemonDetalle?.name ?: ""
                val resultado = cadenaEvolutivaRepository.getId(state.pokemonId, pokemonNombre)  // OJO
                state = state.copy(
                    evolucionDetalle = resultado,
                    isLoadingEvoluciones = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoadingEvoluciones = false,
                    errorEvoluciones = e.localizedMessage ?: "Error al cargar evolución"
                )
            }
        }
    }

    private fun cargarAtaques() {
        if (state.ataquesVisibles.isNotEmpty() || state.isLoadingAtaques) return

        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoadingAtaques = true, errorAtaques = null)
            try {
                val genNombre = state.nombreGeneracionActual
                val idGen = GenerationConstants.getId(genNombre) ?: 0

                val ataquesList = mutableListOf<MoveGeneracionResult>()
                state.pokemonDetalle?.moves?.keys?.forEach { nombreAtaque ->
                    ataqueRepository.getId(
                        idAtaque = 0,  // OJO
                        idGeneracion = idGen,
                        nombreAtaque = nombreAtaque,
                        nombreGeneracion = genNombre
                    )?.let { ataquesList.add(it) }
                }

                state = state.copy(
                    ataquesVisibles = ataquesList,
                    isLoadingAtaques = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoadingAtaques = false,
                    errorAtaques = e.localizedMessage ?: "Error al cargar ataques"
                )
            }
        }
    }

    private fun cargarDetalleEspecificoAtaque(nombreAtaque: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val genNombre = state.nombreGeneracionActual
                val idGen = GenerationConstants.getId(genNombre) ?: 0
                val detalle = ataqueRepository.getId(state.pokemonId, idGen, nombreAtaque, genNombre)  // OJO
                state = state.copy(ataqueSeleccionadoDetalle = detalle)
            } catch (_: Exception) {}
        }
    }

    private fun cargarHabilidades() {
        if (state.habilidadesVisibles.isNotEmpty() || state.isLoadingHabilidades) return

        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoadingHabilidades = true, errorHabilidades = null)
            try {
                val genNombre = state.nombreGeneracionActual
                val idGen = GenerationConstants.getId(genNombre) ?: 0

                val habilidadesList = mutableListOf<AbilityGeneracionResult>()
                state.pokemonDetalle?.abilities?.forEach { slotDto ->
                    habilidadRepository.getId(
                        idHabilidad = 0,  // OJO
                        idGeneracion = idGen,
                        nombreHabilidad = slotDto.ability?.name ?: "",
                        nombreGeneracion = genNombre
                    )?.let { habilidadesList.add(it) }
                }

                state = state.copy(
                    habilidadesVisibles = habilidadesList,
                    isLoadingHabilidades = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoadingHabilidades = false,
                    errorHabilidades = e.localizedMessage ?: "Error al cargar habilidades"
                )
            }
        }
    }

    private fun cargarDetalleEspecificoHabilidad(nombreHabilidad: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val genNombre = state.nombreGeneracionActual
                val idGen = GenerationConstants.getId(genNombre) ?: 0
                val detalle = habilidadRepository.getId(0, idGen, nombreHabilidad, genNombre)
                state = state.copy(habilidadSeleccionadaDetalle = detalle)
            } catch (_: Exception) {}
        }
    }

    private fun cargarMatrizTipos() {
        if (state.tiposVisibles.isNotEmpty() || state.isLoadingTipos) return

        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoadingTipos = true, errorTipos = null)
            try {
                val genNombre = state.nombreGeneracionActual
                val idGen = GenerationConstants.getId(genNombre) ?: 0

                val tiposList = mutableListOf<TypeGeneracionResult>()
                state.pokemonDetalle?.types?.forEach { typeSlot ->
                    tipoRepository.getId(
                        idTipo = 0,
                        idGeneracion = idGen,
                        nombreTipo = typeSlot.type.name,
                        nombreGeneracion = genNombre
                    )?.let { tiposList.add(it) }
                }

                state = state.copy(
                    tiposVisibles = tiposList,
                    isLoadingTipos = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoadingTipos = false,
                    errorTipos = e.localizedMessage ?: "Error al cargar tipos"
                )
            }
        }
    }
}