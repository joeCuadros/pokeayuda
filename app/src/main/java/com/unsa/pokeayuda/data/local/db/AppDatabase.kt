package com.unsa.pokeayuda.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unsa.pokeayuda.data.local.dao.AtaqueDao
import com.unsa.pokeayuda.data.local.dao.CadenaEvolutivaDao
import com.unsa.pokeayuda.data.local.dao.EquipoPokemonDao
import com.unsa.pokeayuda.data.local.dao.GeneracionPokemonDao
import com.unsa.pokeayuda.data.local.dao.HabilidadDao
import com.unsa.pokeayuda.data.local.dao.PokemonDao
import com.unsa.pokeayuda.data.local.dao.TipoDao
import com.unsa.pokeayuda.data.local.entity.AtaqueEntity
import com.unsa.pokeayuda.data.local.entity.CadenaEvolutivaEntity
import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity
import com.unsa.pokeayuda.data.local.entity.GeneracionPokemonEntity
import com.unsa.pokeayuda.data.local.entity.HabilidadEntity
import com.unsa.pokeayuda.data.local.entity.PokemonEntity
import com.unsa.pokeayuda.data.local.entity.TipoEntity

@Database(
    entities = [
        EquipoPokemonEntity::class,
        PokemonEntity::class,
        GeneracionPokemonEntity::class,
        CadenaEvolutivaEntity::class,
        AtaqueEntity::class,
        HabilidadEntity::class,
        TipoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun equipoPokemonDao(): EquipoPokemonDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun generacionPokemonDao(): GeneracionPokemonDao
    abstract fun cadenaEvolutivaDao(): CadenaEvolutivaDao
    abstract fun ataqueDao(): AtaqueDao
    abstract fun habilidadDao(): HabilidadDao
    abstract fun tipoDao(): TipoDao
}