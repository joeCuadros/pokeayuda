package com.unsa.pokeayuda.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unsa.pokeayuda.data.local.entity.CadenaEvolutivaEntity

@Dao
interface CadenaEvolutivaDao {

    @Query("SELECT * FROM cadena_evolutiva")
    suspend fun getAll(): List<CadenaEvolutivaEntity>

    @Query("SELECT * FROM cadena_evolutiva WHERE id = :id")
    suspend fun getId(id: Int): CadenaEvolutivaEntity?

    @Update
    suspend fun update(entity: CadenaEvolutivaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CadenaEvolutivaEntity)

    @Query("DELETE FROM cadena_evolutiva WHERE id = :id")
    suspend fun deleteId(id: Int)

    @Query("DELETE FROM cadena_evolutiva")
    suspend fun deleteAll()

    @Query("""
        SELECT IFNULL(SUM(
            LENGTH(CAST(id AS BLOB)) +
            LENGTH(CAST(fecha AS BLOB)) +
            LENGTH(data)
        ),0)
        FROM cadena_evolutiva
    """)
    suspend fun getSizeBytes(): Long
}