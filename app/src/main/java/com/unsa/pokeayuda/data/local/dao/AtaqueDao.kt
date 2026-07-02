package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.AtaqueEntity

@Dao
interface AtaqueDao {

    @Query("SELECT * FROM ataque")
    suspend fun getAll(): List<AtaqueEntity>

    @Query("""
    SELECT * FROM ataque
        WHERE idAtaque = :idAtaque
        AND idGeneracion = :idGeneracion
    """)
    suspend fun getId(
        idAtaque: Int,
        idGeneracion: Int
    ): AtaqueEntity?

    @Update
    suspend fun update(entity: AtaqueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AtaqueEntity)

    @Query("""
    DELETE FROM ataque
        WHERE idAtaque = :idAtaque
        AND idGeneracion = :idGeneracion
    """)
    suspend fun deleteId(
        idAtaque: Int,
        idGeneracion: Int
    )

    @Query("DELETE FROM ataque")
    suspend fun deleteAll()

    @Query("""
        SELECT IFNULL(SUM(
            LENGTH(CAST(idAtaque AS BLOB)) +
            LENGTH(CAST(idGeneracion AS BLOB)) +
            LENGTH(nombrePokemon) +
            LENGTH(nombreGeneracion) +
            LENGTH(CAST(fecha AS BLOB)) +
            LENGTH(data)
        ),0)
        FROM ataque
    """)
    suspend fun getSizeBytes(): Long
}