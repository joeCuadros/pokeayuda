package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<PokemonEntity>

    @Query("""
    SELECT * FROM pokemon
        WHERE idPokemon = :idPokemon
        AND idGeneracion = :idGeneracion
    """)
    suspend fun getId(
        idPokemon: Int,
        idGeneracion: Int
    ): PokemonEntity?

    @Update
    suspend fun update(entity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PokemonEntity)

    @Query("""
    DELETE FROM pokemon
        WHERE idPokemon = :idPokemon
        AND idGeneracion = :idGeneracion
    """)
    suspend fun deleteId(
        idPokemon: Int,
        idGeneracion: Int
    )

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()

    @Query("""
        SELECT IFNULL(SUM(
            LENGTH(CAST(idPokemon AS BLOB)) +
            LENGTH(CAST(idGeneracion AS BLOB)) +
            LENGTH(nombrePokemon) +
            LENGTH(nombreGeneracion) +
            LENGTH(CAST(fecha AS BLOB)) +
            LENGTH(data)
        ),0)
        FROM pokemon
    """)
    suspend fun getSizeBytes(): Long
}