package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.GeneracionPokemonEntity

@Dao
interface GeneracionPokemonDao {

    @Query("SELECT * FROM generacion_pokemon")
    suspend fun getAll(): List<GeneracionPokemonEntity>

    @Query("SELECT * FROM generacion_pokemon WHERE id = :id")
    suspend fun getId(id: Int): GeneracionPokemonEntity?

    @Update
    suspend fun update(entity: GeneracionPokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: GeneracionPokemonEntity)

    @Query("DELETE FROM generacion_pokemon WHERE id = :id")
    suspend fun deleteId(id: Int)

    @Query("DELETE FROM generacion_pokemon")
    suspend fun deleteAll()

    @Query("""
        SELECT IFNULL(SUM(
            LENGTH(CAST(id AS BLOB)) +
            LENGTH(nombre) +
            LENGTH(CAST(fecha AS BLOB)) +
            LENGTH(data)
        ),0)
        FROM generacion_pokemon
    """)
    suspend fun getSizeBytes(): Long
}