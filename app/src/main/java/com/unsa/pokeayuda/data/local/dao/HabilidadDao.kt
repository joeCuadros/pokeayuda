package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.AtaqueEntity
import com.unsa.pokeayuda.data.local.entity.HabilidadEntity

@Dao
interface HabilidadDao {

    @Query("SELECT * FROM habilidad")
    suspend fun getAll(): List<HabilidadEntity>

    @Query("""
    SELECT * FROM habilidad
        WHERE idPokemon = :idPokemon
        AND idGeneracion = :idGeneracion
    """)
    suspend fun getId(
        idPokemon: Int,
        idGeneracion: Int
    ): HabilidadEntity?

    @Update
    suspend fun update(entity: HabilidadEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HabilidadEntity)

    @Query("""
    DELETE FROM habilidad
        WHERE idPokemon = :idPokemon
        AND idGeneracion = :idGeneracion
    """)
    suspend fun deleteId(
        idPokemon: Int,
        idGeneracion: Int
    )

    @Query("DELETE FROM habilidad")
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
        FROM habilidad
    """)
    suspend fun getSizeBytes(): Long
}