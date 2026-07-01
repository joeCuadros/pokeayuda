package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.AtaqueEntity
import com.unsa.pokeayuda.data.local.entity.TipoEntity

@Dao
interface TipoDao {

    @Query("SELECT * FROM tipo")
    suspend fun getAll(): List<TipoEntity>

    @Query("""
    SELECT * FROM tipo
        WHERE idPokemon = :idPokemon
        AND idGeneracion = :idGeneracion
    """)
    suspend fun getId(
        idPokemon: Int,
        idGeneracion: Int
    ): TipoEntity?

    @Update
    suspend fun update(entity: TipoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TipoEntity)

    @Query("""
    DELETE FROM tipo
        WHERE idPokemon = :idPokemon
        AND idGeneracion = :idGeneracion
    """)
    suspend fun deleteId(
        idPokemon: Int,
        idGeneracion: Int
    )

    @Query("DELETE FROM tipo")
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
        FROM tipo
    """)
    suspend fun getSizeBytes(): Long
}