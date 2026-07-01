package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.EquipoPokemonEntity

@Dao
interface EquipoPokemonDao {

    @Query("SELECT * FROM equipo_pokemon")
    suspend fun getAll(): List<EquipoPokemonEntity>

    @Query("SELECT * FROM equipo_pokemon WHERE id = :id")
    suspend fun getId(id: Int): EquipoPokemonEntity?

    @Update
    suspend fun update(entity: EquipoPokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: EquipoPokemonEntity)

    @Query("DELETE FROM equipo_pokemon WHERE id = :id")
    suspend fun deleteId(id: Int)

    @Query("DELETE FROM equipo_pokemon")
    suspend fun deleteAll()

    @Query("""
        SELECT IFNULL(SUM(
            LENGTH(CAST(id AS BLOB)) +
            LENGTH(CAST(idPokemon AS BLOB))
        ),0)
        FROM equipo_pokemon
    """)
    suspend fun getSizeBytes(): Long
}